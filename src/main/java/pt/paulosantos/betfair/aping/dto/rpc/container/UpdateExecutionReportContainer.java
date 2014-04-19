package pt.paulosantos.betfair.aping.dto.rpc.container;


import com.betfair.aping.betting.entities.UpdateExecutionReport;

/**
 * Created by Paulo. 19-04-2014.
 */
public class UpdateExecutionReportContainer extends Container<UpdateExecutionReport> {
    private UpdateExecutionReport result;

    public UpdateExecutionReport getResult() {
        return result;
    }

    public void setResult(UpdateExecutionReport result) {
        this.result = result;
    }
}
