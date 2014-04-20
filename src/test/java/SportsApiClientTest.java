import com.betfair.aping.betting.entities.*;
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
                                .withBody("{\"jsonrpc\":\"2.0\",\"result\":"+listCompetitionsResponse+",\"id\":\"1\"}")
                );

        MarketFilter filter = new MarketFilter();
        filter.setEventTypeIds(Collections.singleton("1"));

        List<CompetitionResult> competitions = sportsApiRpcClient.listCompetitions(filter, null, executionContext);
        assertListCompetitionResult(competitions);

    }

    private void assertListCompetitionResult(List<CompetitionResult> competitions){
        assertNotNull(competitions);
        assertEquals(competitions.size(), 2);
        assertEquals(competitions.get(0).getCompetition().getName(), "Czech 3. Liga");
        assertEquals(competitions.get(0).getCompetition().getId(),"892425");
        assertEquals(competitions.get(0).getCompetitionRegion(),"CZE");
        assertEquals(competitions.get(0).getMarketCount().intValue(), 8);

        assertEquals(competitions.get(1).getCompetition().getName(), "Indian I League");
        assertEquals(competitions.get(1).getCompetition().getId(),"376041");
        assertEquals(competitions.get(1).getCompetitionRegion(),"IND");
        assertEquals(competitions.get(1).getMarketCount().intValue(), 27);
    }

}
