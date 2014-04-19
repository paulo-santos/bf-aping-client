package pt.paulosantos.betfair.aping.dto.rpc.container;

import com.betfair.aping.betting.entities.MarketProfitAndLoss;

import java.util.List;

/**
 * Created by Paulo. 19-04-2014.
 */
public class MarketProfitAndLossContainer extends Container<List<MarketProfitAndLoss>> {
    private List<MarketProfitAndLoss> result;

    public List<MarketProfitAndLoss> getResult() {
        return result;
    }

    public void setResult(List<MarketProfitAndLoss> result) {
        this.result = result;
    }
}
