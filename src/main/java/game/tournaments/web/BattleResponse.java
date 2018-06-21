package game.tournaments.web;

import java.util.Map;

/**
 * Created by maks on 30.09.17.
 */
public class BattleResponse {

    private String battleId;
    private Map<Long, String> playersBattleTokens;

    public String getBattleId() {
        return battleId;
    }

    public void setBattleId(String battleId) {
        this.battleId = battleId;
    }

    public Map<Long, String> getPlayersBattleTokens() {
        return playersBattleTokens;
    }

    public void setPlayersBattleTokens(Map<Long, String> playersBattleTokens) {
        this.playersBattleTokens = playersBattleTokens;
    }
}
