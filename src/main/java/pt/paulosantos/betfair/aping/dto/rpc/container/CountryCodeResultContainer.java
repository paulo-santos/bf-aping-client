package pt.paulosantos.betfair.aping.dto.rpc.container;


import com.betfair.aping.betting.entities.CountryCodeResult;

import java.util.List;

/**
 * Created by Paulo. 19-04-2014.
 */
public class CountryCodeResultContainer extends Container<List<CountryCodeResult>> {
    private List<CountryCodeResult> result;

    public List<CountryCodeResult> getResult() {
        return result;
    }

    public void setResult(List<CountryCodeResult> result) {
        this.result = result;
    }
}
