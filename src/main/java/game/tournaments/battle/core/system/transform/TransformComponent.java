package game.tournaments.battle.core.system.transform;

import game.tournaments.battle.core.GameObject;
import game.tournaments.battle.core.battlefield.Battlefield;
import game.tournaments.battle.core.battlefield.Direction;
import game.tournaments.battle.core.battlefield.Position;
import game.tournaments.battle.core.component.Component;

import static game.tournaments.battle.core.component.ComponentConstants.*;

/**
 * Created by maks on 24.09.17.
 */
public class TransformComponent extends Component {

    private GameObject gameObject;

    private Position position;
    private Direction direction;

    private Position destinationPosition;
    private Direction destinationDirection;

    private float speed;
    private long lastMoved;

    private boolean isMoving = false;

    public TransformComponent(Position position, Direction direction, GameObject gameObject, float speed) {
        super(TRANSFORM_COMPONENT);
        this.position = position;
        this.direction = direction;
        this.gameObject = gameObject;
        this.speed = speed;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setDestination(Position position){
        if (!this.position.equals(position)){
            destinationPosition = position;
            isMoving = true;
            lastMoved = System.currentTimeMillis();
        }
    }

    public Direction getDestinationDirection() {
        return destinationDirection;
    }

    public void setDestinationDirection(Direction destinationDirection) {
        this.destinationDirection = destinationDirection;
    }

    public Position getDestinationPosition() {
        return destinationPosition;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public long getLastMoved() {
        return lastMoved;
    }

    public void setLastMoved(long lastMoved) {
        this.lastMoved = lastMoved;
    }

    public float getSpeed() {
        return speed;
    }

}
