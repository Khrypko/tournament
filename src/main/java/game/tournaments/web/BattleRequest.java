package game.tournaments.web;

import java.util.List;
import java.util.Map;

/**
 * Created by maks on 30.09.17.
 */
public class BattleRequest {

    private List<Long> playersIds;
    private Map<String, String> params;

    public BattleRequest() {
    }

    public BattleRequest(List<Long> playersIds) {
        this.playersIds = playersIds;
    }

    public List<Long> getPlayersIds() {
        return playersIds;
    }

    public void setPlayersIds(List<Long> playersIds) {
        this.playersIds = playersIds;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
