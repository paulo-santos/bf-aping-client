package pt.paulosantos.betfair.aping.dto.rpc.container;
import pt.paulosantos.betfair.aping.dto.rpc.pojo.Error;

/**
 * Created by Paulo. 19-04-2014.
 */
public abstract class Container<T> {
    private String id;
    private Error error;
    private String jsonrpc;

    public Error getError() {
        return error;
    }
    public void setError(Error error) {
        this.error = error;
    }
    public String getJsonrpc() {
        return jsonrpc;
    }
    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public abstract T getResult();


}
