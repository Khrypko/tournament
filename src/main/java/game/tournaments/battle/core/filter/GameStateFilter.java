package game.tournaments.battle.core.filter;

import game.tournaments.battle.core.state.GameObjectState;

/**
 * Created by maks on 30.09.17.
 */
public interface GameStateFilter {

    boolean filter(GameObjectState state);

}
