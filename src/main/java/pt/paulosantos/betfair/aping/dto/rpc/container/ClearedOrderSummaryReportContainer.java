package pt.paulosantos.betfair.aping.dto.rpc.container;

import com.betfair.aping.betting.entities.ClearedOrderSummaryReport;

/**
 * Created by Paulo. 19-04-2014.
 */
public class ClearedOrderSummaryReportContainer extends Container<ClearedOrderSummaryReport> {
    private ClearedOrderSummaryReport result;

    public ClearedOrderSummaryReport getResult() {
        return result;
    }

    public void setResult(ClearedOrderSummaryReport result) {
        this.result = result;
    }
}
