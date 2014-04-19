package pt.paulosantos.betfair.aping.dto.rpc.container;


import com.betfair.aping.betting.entities.MarketTypeResult;

import java.util.List;

/**
 * Created by Paulo. 19-04-2014.
 */
public class MarketTypeResultContainer extends Container<List<MarketTypeResult>>{
    private List<MarketTypeResult> result;

    public void setResult(List<MarketTypeResult> result) {
        this.result = result;
    }

    public List<MarketTypeResult> getResult() {
        return result;
    }
}
