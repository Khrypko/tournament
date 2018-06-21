package game.tournaments.battle.core.system.transform;

import game.tournaments.battle.core.battlefield.Battlefield;
import game.tournaments.battle.core.battlefield.Direction;
import game.tournaments.battle.core.battlefield.Position;
import game.tournaments.battle.core.command.Command;
import game.tournaments.battle.core.command.exception.IncorrectDataForCommand;

/**
 * Created by maks on 09.10.17.
 */
public class Move extends Command {

    private Position position;
    private Direction direction;
    private TransformComponent transform;

    public Move(Position position, Direction direction, TransformComponent transform, Battlefield battlefield) throws IncorrectDataForCommand {
        checkNewPositionIsValid(battlefield, position);
        this.position = position;
        this.direction = direction;
        this.transform = transform;
    }

    private void checkNewPositionIsValid(Battlefield battlefield, Position position) throws IncorrectDataForCommand {
        if (!battlefield.positionIsOnBattlefield(position))
            throw new IncorrectDataForCommand("position " + position.toString() + " is not on battlefield!");
    }

    @Override
    public void execute() {
        transform.setDestination(position);
        transform.setDestinationDirection(direction);
    }
}
