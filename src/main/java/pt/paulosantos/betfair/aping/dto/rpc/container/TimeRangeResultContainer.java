package pt.paulosantos.betfair.aping.dto.rpc.container;



import com.betfair.aping.betting.entities.TimeRangeResult;

import java.util.List;

/**
 * Created by Paulo. 19-04-2014.
 */
public class TimeRangeResultContainer extends Container<List<TimeRangeResult>>{
    private List<TimeRangeResult> result;

    public List<TimeRangeResult> getResult() {
        return result;
    }

    public void setResult(List<TimeRangeResult> result) {
        this.result = result;
    }
}
