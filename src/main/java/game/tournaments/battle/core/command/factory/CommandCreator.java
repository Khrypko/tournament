package game.tournaments.battle.core.command.factory;

import game.tournaments.battle.core.command.Command;
import game.tournaments.battle.core.command.exception.CommandException;
import game.tournaments.battle.core.command.factory.CommandFactory;
import game.tournaments.convertor.Message;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by maks on 01.10.17.
 */
public class CommandCreator implements CommandFactory {

    private final Logger LOGGER = Logger.getLogger(CommandCreator.class.getName());

    private Map<Integer, CommandFactory> commandFactories;

    public CommandCreator(Map<Integer, CommandFactory> commandFactories) {
        this.commandFactories = commandFactories;
    }

    @Override
    public Command createCommandFromMessage(Message message) throws CommandException {

        validateMessage(message);

        CommandFactory concreteFactory = commandFactories.get(message.getType());
        if (concreteFactory == null)
            throw new CommandException();

        return concreteFactory.createCommandFromMessage(message);

//        return new Command() {
//            @Override
//            public void execute() {
//                System.out.println(message.toString());
//            }
//        };

    }

    private void validateMessage(Message message) throws CommandException {
        if (message == null ||
                message.getPlayerId() == 0 ||
                message.getCommand() == null || message.getCommand().isEmpty()){
            CommandException exception = new CommandException("Bad formatted command. One of the command fields is missing. ");
            LOGGER.warning(exception.getMessage() + "Message is: " + message == null ? "null" : message.toString());
            throw exception;
        }
    }


}
