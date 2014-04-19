package pt.paulosantos.betfair.aping.dto.rpc.container;



import com.betfair.aping.betting.entities.MarketBook;

import java.util.List;

/**
 * Created by Paulo. 19-04-2014.
 */
public class MarketBookContainer extends Container<List<MarketBook>>{
    private List<MarketBook> result;

    public List<MarketBook> getResult() {
        return result;
    }

    public void setResult(List<MarketBook> result) {
        this.result = result;
    }
}
