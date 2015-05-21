import com.betfair.aping.betting.entities.*;
import com.betfair.aping.betting.enums.MarketProjection;
import com.betfair.aping.betting.enums.MarketSort;
import com.google.common.collect.Sets;
import org.apache.commons.io.IOUtils;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pt.paulosantos.betfair.aping.APINGBettingService;
import pt.paulosantos.betfair.aping.core.ExecutionContext;
import pt.paulosantos.betfair.aping.exceptions.APINGException;
import pt.paulosantos.betfair.aping.impl.SportsApiRescriptClient;
import pt.paulosantos.betfair.aping.impl.SportsApiRpcClient;

import java.io.IOException;
import java.util.*;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.StringBody.regex;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by Paulo. 19-04-2014.
 */
@Test
public class SportsApiClientTest {

    private static final String APP_KEY = "APPLICATION_KEY";
    private static String SSO_ID = "SESSION_ID";

    private ExecutionContext executionContext = new ExecutionContext(APP_KEY, SSO_ID);
    private APINGBettingService sportsApiRescriptClient;
    private APINGBettingService sportsApiRpcClient;
    private ClientAndServer mockServer;

    private static final String listCompetitionsResponseMockPath = "/mocks/listCompetitionsResponse.json";
    private static final String listEventTypesResponseMockPath = "/mocks/listEventTypesResponse.json";
    private static final String listEventsResponseMockPath = "/mocks/listEventsResponse.json";
    private static final String tooMuchDataErrorRpcMockPath = "/mocks/tooMuchDataErrorRpc.json";

    @BeforeClass
    public void init() {
        mockServer = startClientAndServer(1801);
        sportsApiRpcClient = new SportsApiRpcClient();
        sportsApiRescriptClient = new SportsApiRescriptClient();
    }

    @AfterClass
    public void shutdown() {
        mockServer.stop();
    }

    private HttpRequest rpcBaseRequest() {
        return request().withMethod("POST")
                .withPath("/exchange/betting/json-rpc/v1")
                .withHeader(new Header("X-Authentication", SSO_ID))
                .withHeader(new Header("X-Application", APP_KEY));
    }

    private HttpRequest rescriptBaseRequest() {
        return request().withMethod("POST")
                .withHeader(new Header("X-Authentication", SSO_ID))
                .withHeader(new Header("X-Application", APP_KEY));
    }

    @Test
    public void testListCompetitionsRescript() throws APINGException, InterruptedException, IOException {

        mockServer.when(
                rescriptBaseRequest()
                        .withPath("/exchange/betting/rest/v1.0/listCompetitions/")
        )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(readFile(listCompetitionsResponseMockPath))
                );

        MarketFilter filter = new MarketFilter();
        filter.setEventTypeIds(Collections.singleton("1"));

        List<CompetitionResult> competitions = sportsApiRescriptClient.listCompetitions(filter, null, executionContext);
        assertListCompetitionResult(competitions);
    }


    @Test
    public void testListCompetitionsRpc() throws APINGException, InterruptedException, IOException {
        mockServer.when(
                rpcBaseRequest()
                        .withBody(regex(".*SportsAPING/v1.0/listCompetitions.*"))
        )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody("{\"jsonrpc\":\"2.0\",\"result\":" + readFile(listCompetitionsResponseMockPath) + ",\"id\":\"1\"}")
                );

        MarketFilter filter = new MarketFilter();
        filter.setEventTypeIds(Collections.singleton("1"));

        List<CompetitionResult> competitions = sportsApiRpcClient.listCompetitions(filter, null, executionContext);
        assertListCompetitionResult(competitions);

    }

    private void assertListCompetitionResult(List<CompetitionResult> competitions) {
        assertNotNull(competitions);
        assertEquals(competitions.size(), 2);
        assertEquals(competitions.get(0).getCompetition().getName(), "Czech 3. Liga");
        assertEquals(competitions.get(0).getCompetition().getId(), "892425");
        assertEquals(competitions.get(0).getCompetitionRegion(), "CZE");
        assertEquals(competitions.get(0).getMarketCount().intValue(), 8);

        assertEquals(competitions.get(1).getCompetition().getName(), "Indian I League");
        assertEquals(competitions.get(1).getCompetition().getId(), "376041");
        assertEquals(competitions.get(1).getCompetitionRegion(), "IND");
        assertEquals(competitions.get(1).getMarketCount().intValue(), 27);
    }

    @Test
    public void testListEventTypes() throws APINGException, IOException {
        mockServer.when(
                rescriptBaseRequest()
                        .withPath("/exchange/betting/rest/v1.0/listEventTypes/"))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(readFile(listEventTypesResponseMockPath))
                );

        MarketFilter filter = new MarketFilter();
        List<EventTypeResult> eventTypes = sportsApiRescriptClient.listEventTypes(filter, null, executionContext);
        verifyListEventTypes(eventTypes);
    }

    @Test
    public void testListEventTypesRpc() throws APINGException, IOException {
        mockServer.when(
                rpcBaseRequest()
                        .withBody(regex(".*SportsAPING/v1.0/listEventTypes.*"))
        )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody("{\"jsonrpc\":\"2.0\",\"result\":" + readFile(listEventTypesResponseMockPath) + ",\"id\":\"1\"}")
                );

        MarketFilter filter = new MarketFilter();
        List<EventTypeResult> eventTypes = sportsApiRpcClient.listEventTypes(filter, null, executionContext);
        verifyListEventTypes(eventTypes);
    }

    private void verifyListEventTypes(List<EventTypeResult> eventTypeResults) {
        assertNotNull(eventTypeResults);
        assertEquals(eventTypeResults.size(), 3);

        assertEquals(eventTypeResults.get(0).getEventType().getId(), "1");
        assertEquals(eventTypeResults.get(0).getEventType().getName(), "Soccer");
        assertEquals(eventTypeResults.get(0).getMarketCount().intValue(), 21689);

        assertEquals(eventTypeResults.get(1).getEventType().getId(), "2");
        assertEquals(eventTypeResults.get(1).getEventType().getName(), "Tennis");
        assertEquals(eventTypeResults.get(1).getMarketCount().intValue(), 886);

        assertEquals(eventTypeResults.get(2).getEventType().getId(), "3");
        assertEquals(eventTypeResults.get(2).getEventType().getName(), "Golf");
        assertEquals(eventTypeResults.get(2).getMarketCount().intValue(), 75);
    }

    @Test
    public void testListEventsResponseRescript() throws APINGException, IOException {
        mockServer.when(
                rescriptBaseRequest()
                        .withPath("/exchange/betting/rest/v1.0/listEvents/")
        )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(readFile(listEventsResponseMockPath))
                );
        MarketFilter filter = new MarketFilter();
        List<EventResult> eventResults = sportsApiRescriptClient.listEvents(filter, null, executionContext);
        validateListEventsResponse(eventResults);
    }

    @Test
    public void testListEventsResponseRescriptRpc() throws APINGException, IOException {
        mockServer.when(
                rpcBaseRequest()
                        .withBody(regex(".*SportsAPING/v1.0/listEvents.*"))

        )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody("{\"jsonrpc\":\"2.0\",\"result\":" + readFile(listEventsResponseMockPath) + ",\"id\":\"1\"}")
                );
        MarketFilter filter = new MarketFilter();
        List<EventResult> eventResults = sportsApiRescriptClient.listEvents(filter, null, executionContext);
        validateListEventsResponse(eventResults);
    }

    private void validateListEventsResponse(List<EventResult> eventResults) {
        assertNotNull(eventResults);
        assertEquals(eventResults.size(), 2);
        assertEquals(eventResults.get(0).getMarketCount().intValue(), 25);
        assertEquals(eventResults.get(0).getEvent().getId(), "27113620");
        assertEquals(eventResults.get(0).getEvent().getName(), "USA v Germany");
        assertEquals(eventResults.get(0).getEvent().getCountryCode(), "BR");

        assertEquals(eventResults.get(1).getMarketCount().intValue(), 25);
        assertEquals(eventResults.get(1).getEvent().getId(), "27113622");
        assertEquals(eventResults.get(1).getEvent().getName(), "South Korea v Belgium");
        assertEquals(eventResults.get(1).getEvent().getCountryCode(), "BR");
    }

    @Test(expectedExceptions = APINGException.class)
    public void shouldThrowExceptionRpc() throws APINGException, IOException {
        mockServer.when(
                rpcBaseRequest()
                        .withBody(regex(".*SportsAPING/v1.0/listMarketCatalogue.*\"maxResults\":1000.*"))

        )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(readFile(tooMuchDataErrorRpcMockPath))
                );
        MarketFilter filter = new MarketFilter();
        filter.setEventTypeIds(new HashSet<>(Arrays.asList("1", "2", "7")));

        Set<MarketProjection> marketProjectionSet = Sets.newHashSet();
        marketProjectionSet.add(MarketProjection.COMPETITION);
        marketProjectionSet.add(MarketProjection.EVENT);
        marketProjectionSet.add(MarketProjection.EVENT_TYPE);
        marketProjectionSet.add(MarketProjection.MARKET_DESCRIPTION);
        marketProjectionSet.add(MarketProjection.MARKET_START_TIME);
        marketProjectionSet.add(MarketProjection.RUNNER_DESCRIPTION);
        marketProjectionSet.add(MarketProjection.RUNNER_METADATA);

        sportsApiRpcClient.listMarketCatalogue(filter, marketProjectionSet, MarketSort.MAXIMUM_TRADED, 1000, null, executionContext);

    }

    private String readFile(String classpath) throws IOException {
        return IOUtils.toString(this.getClass().getResourceAsStream(classpath));
    }
}
