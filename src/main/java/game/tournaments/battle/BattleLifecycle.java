package game.tournaments.battle;

import game.tournaments.battle.exception.BattleIsActiveYet;

/**
 * Created by maks on 28.09.17.
 */
public interface BattleLifecycle {

    public void startBattle(BattleController battleController);

    public void endBattle(BattleResults results);

}
