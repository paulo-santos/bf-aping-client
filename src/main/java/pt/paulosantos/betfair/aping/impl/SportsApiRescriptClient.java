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
import pt.paulosantos.betfair.aping.exceptions.ErrorCode;

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

    private static final String BASE_URL = properties.getProperty(HOST_PROPERTY, HOST) +
                                           properties.getProperty(BETTING_BASE_PATH_PROPERTY, BETTING_BASE_PATH) +
                                           properties.getProperty(REST_PATH_PROPERTY, REST_PATH);

    @Override
    protected <T> T execute(ExecutionContext executionContext, final ApiNgOperation operation, Map<Parameter, Object> params) throws APINGException {

        T result = null;

        try {
            ContentResponse response = httpClient
                    .POST(BASE_URL + operation.toString() + "/")
                    .header(properties.getProperty(APP_KEY_HEADER_PROPERTY), executionContext.getApplicationKey())
                    .header(properties.getProperty(SESSION_TOKEN_HEADER_PROPERTY), executionContext.getSessionToken())
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Accept-Encoding", "gzip, deflate")
                    .content(new StringContentProvider(objectMapper.writeValueAsString(params)))
                    .send();

            if (response.getStatus() == HttpStatus.OK_200) {
                result = objectMapper.readValue(response.getContentAsString(), operation.getResponseType());
            } else {
                throw objectMapper.readValue(response.getContentAsString(), APINGException.class);
            }

        } catch (InterruptedException | ExecutionException e) {
            LOG.error("Unable to send request", e);
        } catch (TimeoutException e) {
            throw new APINGException(e.getMessage(), ErrorCode.TIMEOUT_ERROR, null);
        } catch (JsonProcessingException e) {
            LOG.error("Error processing json", e);
        } catch (IOException e) {
            LOG.error("Error mapping response to java object", e);
        }

        return result;
    }
}
