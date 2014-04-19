package pt.paulosantos.betfair.aping.dto.rpc.container;


import com.betfair.aping.betting.entities.CurrentOrderSummaryReport;

/**
 * Created by Paulo. 19-04-2014.
 */
public class CurrentOrderSummaryReportContainer extends Container<CurrentOrderSummaryReport>{
    private CurrentOrderSummaryReport result;

    public CurrentOrderSummaryReport getResult() {
        return result;
    }

    public void setResult(CurrentOrderSummaryReport result) {
        this.result = result;
    }
}
