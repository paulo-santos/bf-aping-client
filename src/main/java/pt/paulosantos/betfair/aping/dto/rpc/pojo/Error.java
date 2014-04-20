package pt.paulosantos.betfair.aping.dto.rpc.pojo;

/**
 * Created by Paulo. 19-04-2014.
 */
public class Error {
    private String message;
    private Integer code;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
