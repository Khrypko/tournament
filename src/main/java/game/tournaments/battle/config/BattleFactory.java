package game.tournaments.battle.config;

import game.tournaments.battle.BattleController;
import game.tournaments.battle.config.BattleDetails;
import game.tournaments.battle.config.battlefield.ecxeption.InsufficientDataToCreateBattlefield;
import game.tournaments.battle.exception.BattleCreationException;

/**
 * Created by maks on 28.09.17.
 */
public interface BattleFactory {

    BattleController createBattle(BattleDetails configuration) throws BattleCreationException, InsufficientDataToCreateBattlefield;

}
