package game.tournaments.battle.config.battlefield;

import game.tournaments.battle.config.BattleDetails;
import game.tournaments.battle.config.battlefield.ecxeption.InsufficientDataToCreateBattlefield;
import game.tournaments.battle.core.battlefield.Battlefield;
import game.tournaments.battle.exception.BattleCreationException;

/**
 * Created by maks on 15.10.17.
 */
public interface BattlefieldFactory {

    public Battlefield createBattlefield(BattleDetails details) throws InsufficientDataToCreateBattlefield, BattleCreationException;

}
