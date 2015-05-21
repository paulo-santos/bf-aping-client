package pt.paulosantos.betfair.aping.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.paulosantos.betfair.aping.AbstractSportsApiClient;
import pt.paulosantos.betfair.aping.core.ExecutionContext;
import pt.paulosantos.betfair.aping.dto.rpc.container.Container;
import pt.paulosantos.betfair.aping.dto.rpc.request.JsonRpcRequest;
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
public class SportsApiRpcClient extends AbstractSportsApiClient {
    private static final Logger LOG = LoggerFactory.getLogger(SportsApiRpcClient.class);

    private static final String RPC_PATH_PROPERTY = "aping.betting.rpc.path";
    private static final String RPC_METHOD_PREFIX_PROPERTY = "aping.betting.rpc.methodPrefix";

    private static final String RPC_PATH = "/json-rpc/v1";
    private static final String RPC_METHOD_PREFIX = "SportsAPING/v1.0/";

    private static final String URL = properties.getProperty(HOST_PROPERTY, HOST) +
                                      properties.getProperty(BETTING_BASE_PATH_PROPERTY, BETTING_BASE_PATH) +
                                      properties.getProperty(RPC_PATH_PROPERTY, RPC_PATH);

    @Override
    protected <T> T execute(ExecutionContext executionContext, ApiNgOperation operation, Map<Parameter, Object> params) throws APINGException {

        T result = null;

        JsonRpcRequest request = new JsonRpcRequest();
        request.setId("1");
        request.setMethod(properties.getProperty(RPC_METHOD_PREFIX_PROPERTY, RPC_METHOD_PREFIX) + operation.toString());
        request.setParams(params);

        try {
            ContentResponse response  = httpClient
                    .POST(URL)
                    .header(properties.getProperty(APP_KEY_HEADER_PROPERTY), executionContext.getApplicationKey())
                    .header(properties.getProperty(SESSION_TOKEN_HEADER_PROPERTY), executionContext.getSessionToken())
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Accept-Encoding", "gzip, deflate")
                    .content(new StringContentProvider(objectMapper.writeValueAsString(request)))
                    .send();

            @SuppressWarnings("unchecked")
            Container<T> responseContainer = objectMapper.readValue(response.getContentAsString(), operation.getRpcResponseType());
            if (responseContainer.getError() != null) {
                throw responseContainer.getError().getData().getAPINGException();
            } else {
                result = responseContainer.getResult();
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
