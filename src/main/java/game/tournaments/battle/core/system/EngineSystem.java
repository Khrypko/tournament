package game.tournaments.battle.core.system;

import game.tournaments.battle.core.component.Component;
import game.tournaments.battle.core.component.exception.IncorrectComponentType;

/**
 * Created by maks on 24.09.17.
 */
public interface EngineSystem {

    public int getPriority();

    public void execute();

    public void addComponent(Component component) throws IncorrectComponentType;
    public void removeComponent(Component component) throws IncorrectComponentType;


}
