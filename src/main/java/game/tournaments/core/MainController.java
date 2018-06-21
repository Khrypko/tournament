package game.tournaments.core;

import game.tournaments.battle.BattleController;
import game.tournaments.battle.config.BattleDetails;
import game.tournaments.battle.config.battlefield.ecxeption.InsufficientDataToCreateBattlefield;
import game.tournaments.battle.exception.BattleCreationException;
import game.tournaments.battle.exception.BattleIsActiveYet;

import java.util.Optional;

/**
 * Created by maks on 28.09.17.
 * Contains logic for creation of the battle and responsible for controlling pool of battle executors;
 */
public interface MainController {

    BattleController createBattle(BattleDetails configuration) throws BattleCreationException, InsufficientDataToCreateBattlefield;

    void removeBattle(String id) throws BattleIsActiveYet;

    boolean battleExists(String battleId);

    Optional<BattleController> getBattleController(String id);

}
