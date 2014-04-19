package pt.paulosantos.betfair.aping.dto.rpc.container;



import com.betfair.aping.betting.entities.CompetitionResult;

import java.util.List;

/**
 * Created by Paulo. 19-04-2014.
 */
public class CompetitionResultContainer extends Container<List<CompetitionResult>> {
    private List<CompetitionResult> result;

    public List<CompetitionResult> getResult() {
        return result;
    }
    public void setResult(List<CompetitionResult> result) {
        this.result = result;
    }
}
