package game.tournaments.battle.core.system.transform;

import game.tournaments.battle.core.GameEngine;
import game.tournaments.battle.core.battlefield.Battlefield;
import game.tournaments.battle.core.battlefield.Position;
import game.tournaments.battle.core.component.Component;
import game.tournaments.battle.core.component.exception.IncorrectComponentType;
import game.tournaments.battle.core.state.GameObjectState;
import game.tournaments.battle.core.state.StateChange;
import game.tournaments.battle.core.system.EngineSystem;
import game.tournaments.battle.exception.PositionOccupied;

import static game.tournaments.battle.core.component.ComponentConstants.*;
import static game.tournaments.battle.core.state.ChangeType.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by maks on 09.10.17.
 */
public class TransformSystem implements EngineSystem {

    private int priority;
    private Battlefield battlefield;
    private GameEngine gameEngine;
    private Set<TransformComponent> components = new LinkedHashSet<>();

    public TransformSystem(int priority, Battlefield battlefield, GameEngine gameEngine) {
        this.priority = priority;
        this.battlefield = battlefield;
        this.gameEngine = gameEngine;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public void execute() {
        components.stream().forEach(this::move);
    }

    public void move(TransformComponent component){
        if (component.isMoving()){
            long currentTime = System.currentTimeMillis();
            float secondsPassed = (currentTime - component.getLastMoved()) / 1000f;
            int numberOfMovesShouldBeDone = (int) (secondsPassed * component.getSpeed());

            while (numberOfMovesShouldBeDone > 0){
                tryMove(component);
                if (component.getPosition().equals(component.getDestinationPosition())){
                    component.setMoving(false);
                    component.setDirection(component.getDestinationDirection());
                    return;
                }
                numberOfMovesShouldBeDone--;
            }
        }
    }

    private void tryMove(TransformComponent component){
        try {
            Position position = battlefield.getNextPositionToMove(component.getPosition(), component.getDestinationPosition());
            battlefield.setObject(position, component.getGameObject());
            component.setPosition(position);
            component.setLastMoved(System.currentTimeMillis());
            setThisTurnChanges(component);
        } catch (PositionOccupied positionOccupied) {}

    }

    private void setThisTurnChanges(TransformComponent component) {
        GameObjectState state = gameEngine.getGameObjectState(component.getGameObject().getId());
        StateChange stateChange = state.getOrCreateStateChange(TRANSFORM_COMPONENT);
        stateChange.addChange(MOVEMENT, "position", component.getPosition());
        stateChange.addChange(MOVEMENT, "direction", component.getDirection());
    }

    @Override
    public void addComponent(Component component) throws IncorrectComponentType {
        try {
            components.add((TransformComponent) component);
        } catch (ClassCastException e){
            throw new IncorrectComponentType("expected " + TransformComponent.class + ", passed " + component.getClass());
        }
    }

    @Override
    public void removeComponent(Component component) throws IncorrectComponentType {
        this.components.remove(component);
    }



}
