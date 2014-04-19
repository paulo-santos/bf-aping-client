package pt.paulosantos.betfair.aping.dto.rpc.container;


import com.betfair.aping.betting.entities.ReplaceExecutionReport;

/**
 * Created by Paulo. 19-04-2014.
 */
public class ReplaceExecutionReportContainer extends Container<ReplaceExecutionReport> {
    private ReplaceExecutionReport result;

    public ReplaceExecutionReport getResult() {
        return result;
    }

    public void setResult(ReplaceExecutionReport result) {
        this.result = result;
    }
}
