package pt.paulosantos.betfair.aping.dto.rpc.pojo;

import pt.paulosantos.betfair.aping.exceptions.APINGException;

/**
 * Created by Paulo. 19-04-2014.
 */
public class Data {
    private APINGException APINGException;

    public APINGException getAPINGException() {
        return APINGException;
    }

    public void setAPINGException(APINGException aPINGException) {
        APINGException = aPINGException;
    }
}
