package game.tournaments.battle.core.command.factory;

import game.tournaments.battle.core.command.Command;
import game.tournaments.battle.core.command.exception.CommandException;
import game.tournaments.convertor.Message;

/**
 * Created by maks on 01.10.17.
 */
public interface CommandFactory {

    public static final int TRANSFORM_COMMAND = 1;

    Command createCommandFromMessage(Message message) throws CommandException;

}
