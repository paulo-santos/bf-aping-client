package pt.paulosantos.betfair.aping.dto.rpc.container;


import com.betfair.aping.betting.entities.PlaceExecutionReport;

/**
 * Created by Paulo. 19-04-2014.
 */
public class PlaceExecutionReportContainer extends Container<PlaceExecutionReport> {
    private PlaceExecutionReport result;

    public PlaceExecutionReport getResult() {
        return result;
    }

    public void setResult(PlaceExecutionReport result) {
        this.result = result;
    }
}
