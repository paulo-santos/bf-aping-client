package pt.paulosantos.betfair.aping.dto.rpc.container;


import com.betfair.aping.betting.entities.CancelExecutionReport;

/**
 * Created by Paulo. 19-04-2014.
 */
public class CancelExecutionReportContainer extends Container<CancelExecutionReport>{
    private CancelExecutionReport result;

    public CancelExecutionReport getResult() {
        return result;
    }

    public void setResult(CancelExecutionReport result) {
        this.result = result;
    }
}
