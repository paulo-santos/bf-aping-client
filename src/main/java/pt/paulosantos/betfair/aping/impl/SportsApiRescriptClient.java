package pt.paulosantos.betfair.aping.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.paulosantos.betfair.aping.AbstractSportsApiClient;
import pt.paulosantos.betfair.aping.core.ExecutionContext;
import pt.paulosantos.betfair.aping.enums.ApiNgOperation;
import pt.paulosantos.betfair.aping.enums.Parameter;
import pt.paulosantos.betfair.aping.exceptions.APINGException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Paulo. 19-04-2014.
 */
public class SportsApiRescriptClient extends AbstractSportsApiClient {
    private static final Logger LOG = LoggerFactory.getLogger(SportsApiRescriptClient.class);

    private static final String REST_PATH_PROPERTY = "aping.betting.rescript.path";
    private static final String REST_PATH = "/rest/v1.0/";

    @Override
    protected <T> T execute(ExecutionContext executionContext, final ApiNgOperation operation, Map<Parameter, Object> params) throws APINGException {

        String url = properties.getProperty(HOST_PROPERTY, HOST) +
                     properties.getProperty(BETTING_BASE_PATH_PROPERTY, BETTING_BASE_PATH) +
                     properties.getProperty(REST_PATH_PROPERTY, REST_PATH) +
                     operation.toString() + "/";

        try {
            ContentResponse response = httpClient
                    .POST(url)
                    .header(APP_KEY_HEADER, executionContext.getApplicationKey())
                    .header(SESSION_TOKEN_HEADER, executionContext.getSessionToken())
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Accept-Encoding", "gzip, deflate")
                    .content(new StringContentProvider(objectMapper.writeValueAsString(params)))
                    .send();

            if (response.getStatus() == HttpStatus.OK_200) {
                return objectMapper.readValue(response.getContentAsString(), operation.getResponseType());
            } else {
                throw objectMapper.readValue(response.getContentAsString(), APINGException.class);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}