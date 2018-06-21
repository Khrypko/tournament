package game.tournaments.convertor.factory;

import game.tournaments.battle.core.GameObject;
import game.tournaments.battle.core.battlefield.Battlefield;
import game.tournaments.battle.core.battlefield.Direction;
import game.tournaments.battle.core.battlefield.Position;
import game.tournaments.battle.core.command.Command;
import game.tournaments.battle.core.command.exception.CommandException;
import game.tournaments.battle.core.command.factory.CommandFactory;
import game.tournaments.battle.core.component.Component;
import game.tournaments.battle.core.system.transform.Move;
import game.tournaments.battle.core.system.transform.TransformComponent;
import game.tournaments.convertor.ConverterUtil;
import game.tournaments.convertor.Message;
import static game.tournaments.battle.core.component.ComponentConstants.*;

import java.util.Map;
import java.util.Optional;

/**
 * Created by maks on 09.10.17.
 * Creates Move command
 * Message command map should contain:
 *  - game object - "object"
 *  - position x - "x"
 *  - position y - "y"
 *  - position z - "z"
 *  - direction  = "direction";
 *
 */
public class TransformCommandFactory implements CommandFactory {

    private static final String OBJECT = "object";
    private static final String X = "x";
    private static final String Y = "y";
    private static final String Z = "z";
    private static final String DIRECTION = "direction";

    private Battlefield battlefield;

    public TransformCommandFactory(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    @Override
    public Command createCommandFromMessage(Message message) throws CommandException {
        if (message.getType() != CommandFactory.TRANSFORM_COMMAND)
            throw new CommandException();

        Map<String, String> parameters = message.getCommand();

        TransformComponent transform = getTransformComponent(parameters, message.getPlayerId());
        Position position = getPositionFromParameters(parameters);
        Direction direction = getDirectionFromParameters(parameters);

        return new Move(position, direction, transform, battlefield);

    }

    private Direction getDirectionFromParameters(Map<String, String> parameters) throws CommandException {
        Optional<Integer> directionIndex = ConverterUtil.convertStringToInteger(parameters.get(DIRECTION));
        if (directionIndex.isPresent()){
            Optional<Direction> direction = Direction.getDirectionByIndex(directionIndex.get());
            if (direction.isPresent())
                return direction.get();
        }

        throw new CommandException("cant find such direction");
    }

    private TransformComponent getTransformComponent(Map<String, String> parameters, Long ownerId) throws CommandException {
        Optional<Long> objectId = ConverterUtil.convertStringToLong(parameters.get(OBJECT));
        if (!objectId.isPresent())
            throw new CommandException("Incorrect object id");

        Optional<GameObject> object = battlefield.getObjectById(objectId.get());
        if (!object.isPresent())
            throw new CommandException("no such game object");

        if (object.get().getOwner() != ownerId)
            throw new CommandException("Player does not own this object!");

        Component transform = object.get().getComponent(TRANSFORM_COMPONENT);
        if (transform == null)
            throw new CommandException("Object do not have transform component");

        try {
            return (TransformComponent) transform;
        } catch (ClassCastException e){
            throw new CommandException("It's not transform component!!!");
        }

    }

    private Position getPositionFromParameters(Map<String, String> parameters) throws CommandException {
        Optional<Integer> x = ConverterUtil.convertStringToInteger(parameters.get(X));
        Optional<Integer> y = ConverterUtil.convertStringToInteger(parameters.get(Y));
        Optional<Integer> z = ConverterUtil.convertStringToInteger(parameters.get(Z));

        if (!(x.isPresent() && y.isPresent() && z.isPresent()))
            throw new CommandException("not present position parameters");

        return new Position(x.get(), y.get(), z.get());
    }

}
