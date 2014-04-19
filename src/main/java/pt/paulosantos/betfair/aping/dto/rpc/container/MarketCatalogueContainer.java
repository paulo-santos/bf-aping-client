package pt.paulosantos.betfair.aping.dto.rpc.container;


import com.betfair.aping.betting.entities.MarketCatalogue;

import java.util.List;

/**
 * Created by Paulo. 19-04-2014.
 */
public class MarketCatalogueContainer extends Container<List<MarketCatalogue>>{
    private List<MarketCatalogue> result;

    public List<MarketCatalogue> getResult() {
        return result;
    }

    public void setResult(List<MarketCatalogue> result) {
        this.result = result;
    }
}
