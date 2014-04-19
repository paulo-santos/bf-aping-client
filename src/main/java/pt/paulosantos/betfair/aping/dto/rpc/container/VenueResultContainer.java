package pt.paulosantos.betfair.aping.dto.rpc.container;



import com.betfair.aping.betting.entities.VenueResult;

import java.util.List;

/**
 * Created by Paulo. 19-04-2014.
 */
public class VenueResultContainer extends Container<List<VenueResult>>{
    private List<VenueResult> result;

    public List<VenueResult> getResult() {
        return result;
    }

    public void setResult(List<VenueResult> result) {
        this.result = result;
    }
}
