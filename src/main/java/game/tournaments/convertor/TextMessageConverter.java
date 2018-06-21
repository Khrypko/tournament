package game.tournaments.convertor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.tournaments.battle.core.command.Command;
import game.tournaments.battle.core.command.exception.CommandException;
import game.tournaments.battle.core.command.factory.CommandFactory;
import game.tournaments.battle.core.filter.GameStateFilter;
import game.tournaments.battle.core.state.GameObjectState;
import game.tournaments.battle.exception.MessageConvertionFailed;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by maks on 30.09.17.
 */
public class TextMessageConverter implements MessageConvertor {

    private CommandFactory commandFactory;

    public TextMessageConverter(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    @Override
    public List<Command> convertMessagesToCommands(Collection<WebSocketMessage> messages) {
        return null;
    }

    @Override
    public Command convertMessageToCommand(WebSocketMessage message) throws MessageConvertionFailed, CommandException {

        ObjectMapper mapper = new ObjectMapper();
        try {
            Message msg = mapper.readValue((String)message.getPayload(), Message.class);
            return commandFactory.createCommandFromMessage(msg);
        } catch (IOException e) {
            throw new MessageConvertionFailed();
        }

    }

    @Override
    public WebSocketMessage createMessageFromObjectStates(Collection<GameObjectState> states, GameStateFilter filter) throws MessageConvertionFailed {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String textMessage = mapper.writeValueAsString(states);
            return new TextMessage(textMessage);
        } catch (JsonProcessingException e) {
            throw new MessageConvertionFailed();
        }

    }
}
