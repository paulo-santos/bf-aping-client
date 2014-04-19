package pt.paulosantos.betfair.aping.dto.rpc.container;



import com.betfair.aping.betting.entities.EventTypeResult;

import java.util.List;

/**
 * Created by Paulo. 19-04-2014.
 */
public class EventTypeResultContainer extends Container<List<EventTypeResult>>{
    private List<EventTypeResult> result;

    public List<EventTypeResult> getResult() {
        return result;
    }

    public void setResult(List<EventTypeResult> result) {
        this.result = result;
    }
}
