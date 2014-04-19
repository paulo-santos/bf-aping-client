package pt.paulosantos.betfair.aping.dto.rpc.container;



import com.betfair.aping.betting.entities.EventResult;

import java.util.List;

/**
 * Created by Paulo. 19-04-2014.
 */
public class EventResultContainer extends Container<List<EventResult>>{
    private List<EventResult> result;

    public List<EventResult> getResult() {
        return result;
    }

    public void setResult(List<EventResult> result) {
        this.result = result;
    }
}
