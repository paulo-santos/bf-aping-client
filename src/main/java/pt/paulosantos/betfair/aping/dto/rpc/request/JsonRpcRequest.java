package pt.paulosantos.betfair.aping.dto.rpc.request;

import pt.paulosantos.betfair.aping.enums.Parameter;

import java.util.Map;

/**
 * Created by Paulo. 19-04-2014.
 */
public class JsonRpcRequest {
    private String jsonrpc = "2.0";
    private String method;
    private String id;
    private Map<Parameter, Object> params;

    public String getJsonrpc() {
        return jsonrpc;
    }
    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Map<Parameter, Object> getParams() {
        return params;
    }
    public void setParams(Map<Parameter, Object> params) {
        this.params = params;
    }
}
