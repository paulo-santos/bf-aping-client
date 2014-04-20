import com.betfair.aping.betting.entities.*;
import com.betfair.aping.betting.enums.MarketProjection;
import com.betfair.aping.betting.enums.MarketSort;
import com.google.common.collect.Sets;
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

    private static final String listCompetitionsResponse = "[{\"competition\":{\"id\":\"892425\",\"name\":\"Czech 3. Liga\"},\"marketCount\":8,\"competitionRegion\":\"CZE\"},{\"competition\":{\"id\":\"376041\",\"name\":\"Indian I League\"},\"marketCount\":27,\"competitionRegion\":\"IND\"}]";
    private static final String listEventTypesResponse = "[{\"eventType\":{\"id\":\"1\",\"name\":\"Soccer\"},\"marketCount\":21689},{\"eventType\":{\"id\":\"2\",\"name\":\"Tennis\"},\"marketCount\":886},{\"eventType\":{\"id\":\"3\",\"name\":\"Golf\"},\"marketCount\":75}]";
    private static final String listEventsResponse = "[{\"event\":{\"id\":\"27113620\",\"name\":\"USA v Germany\",\"countryCode\":\"BR\",\"timezone\":\"Europe/London\",\"openDate\":\"2014-06-26T16:00:00.000Z\"},\"marketCount\":25},{\"event\":{\"id\":\"27113622\",\"name\":\"South Korea v Belgium\",\"countryCode\":\"BR\",\"timezone\":\"Europe/London\",\"openDate\":\"2014-06-26T20:00:00.000Z\"},\"marketCount\":25}]";

    private static final String tooMuchDataErrorRpc = "{\"jsonrpc\":\"2.0\",\"error\":{\"code\":-32099,\"message\":\"ANGX-0001\",\"data\":{\"exceptionname\":\"APINGException\",\"APINGException\":{\"errorDetails\":\"operation restricts amount of data being returned\",\"errorCode\":\"TOO_MUCH_DATA\",\"requestUUID\":\"prdang003-04020803-0042e49144\"}}},\"id\":\"1\"}";

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
    public void testListCompetitionsRescript() throws APINGException, InterruptedException {

        mockServer.when(
                rescriptBaseRequest()
                        .withPath("/exchange/betting/rest/v1.0/listCompetitions/")
        )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(listCompetitionsResponse)
                );

        MarketFilter filter = new MarketFilter();
        filter.setEventTypeIds(Collections.singleton("1"));

        List<CompetitionResult> competitions = sportsApiRescriptClient.listCompetitions(filter, null, executionContext);
        assertListCompetitionResult(competitions);
    }


    @Test
    public void testListCompetitionsRpc() throws APINGException, InterruptedException {
        mockServer.when(
                rpcBaseRequest()
                        .withBody(regex(".*SportsAPING/v1.0/listCompetitions.*"))
        )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody("{\"jsonrpc\":\"2.0\",\"result\":" + listCompetitionsResponse + ",\"id\":\"1\"}")
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
    public void testListEventTypes() throws APINGException {
        mockServer.when(
                rescriptBaseRequest()
                        .withPath("/exchange/betting/rest/v1.0/listEventTypes/"))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(listEventTypesResponse)
                );

        MarketFilter filter = new MarketFilter();
        List<EventTypeResult> eventTypes = sportsApiRescriptClient.listEventTypes(filter, null, executionContext);
        verifyListEventTypes(eventTypes);
    }

    @Test
    public void testListEventTypesRpc() throws APINGException {
        mockServer.when(
                rpcBaseRequest()
                        .withBody(regex(".*SportsAPING/v1.0/listEventTypes.*"))
        )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody("{\"jsonrpc\":\"2.0\",\"result\":" + listEventTypesResponse + ",\"id\":\"1\"}")
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
    public void testListEventsResponseRescript() throws APINGException {
        mockServer.when(
                rescriptBaseRequest()
                        .withPath("/exchange/betting/rest/v1.0/listEvents/")
        )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(listEventsResponse)
                );
        MarketFilter filter = new MarketFilter();
        List<EventResult> eventResults = sportsApiRescriptClient.listEvents(filter, null, executionContext);
        validateListEventsResponse(eventResults);
    }

    @Test
    public void testListEventsResponseRescriptRpc() throws APINGException {
        mockServer.when(
                rpcBaseRequest()
                        .withBody(regex(".*SportsAPING/v1.0/listEvents.*"))

        )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody("{\"jsonrpc\":\"2.0\",\"result\":" + listEventsResponse + ",\"id\":\"1\"}")
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
    public void shouldThrowExceptionRpc() throws APINGException {
        mockServer.when(
                rpcBaseRequest()
                        .withBody(regex(".*SportsAPING/v1.0/listMarketCatalogue.*\"maxResults\":1000.*"))

        )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(tooMuchDataErrorRpc)
                );
        MarketFilter filter = new MarketFilter();
        filter.setEventTypeIds(new HashSet<String>(Arrays.asList("1", "2", "7")));

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
}
