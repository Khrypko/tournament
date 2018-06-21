package game.tournaments.battle.config;

import game.tournaments.battle.BattleLifecycle;
import game.tournaments.battle.core.battlefield.BattlefieldType;
import game.tournaments.web.BattleRequest;

import java.util.List;
import java.util.Map;

/**
 * Created by maks on 30.09.17.
 */
public class BattleDetails {

    private BattleLifecycle battleLifecycle;
    private List<Long> playersIds;
    private Map<String, String> parameters;

    public BattleDetails() {
    }

    public BattleDetails(List<Long> playersIds) {
        this.playersIds = playersIds;
    }

    public BattleDetails(BattleLifecycle battleLifecycle, List<Long> playersIds) {
        this.battleLifecycle = battleLifecycle;
        this.playersIds = playersIds;
    }

    public void setBattleLifecycle(BattleLifecycle battleLifecycle) {
        this.battleLifecycle = battleLifecycle;
    }

    public BattleLifecycle getBattleLifecycle() {
        return battleLifecycle;
    }

    public List<Long> getPlayersIds() {
        return playersIds;
    }

    public void setPlayersIds(List<Long> playersIds) {
        this.playersIds = playersIds;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static BattleDetails createFromRequest(BattleRequest request){
        BattleDetails details = new BattleDetails(request.getPlayersIds());
        details.parameters = request.getParams();
        return details;
    }

}
