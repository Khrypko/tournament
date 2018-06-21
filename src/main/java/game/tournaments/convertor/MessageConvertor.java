package game.tournaments.convertor;

import game.tournaments.battle.core.command.Command;
import game.tournaments.battle.core.command.exception.CommandException;
import game.tournaments.battle.core.filter.GameStateFilter;
import game.tournaments.battle.core.state.GameObjectState;
import game.tournaments.battle.exception.MessageConvertionFailed;
import org.springframework.web.socket.WebSocketMessage;

import java.util.Collection;
import java.util.List;

/**
 * Created by maks on 24.09.17.
 */
public interface MessageConvertor {

    List<Command> convertMessagesToCommands(Collection<WebSocketMessage> messages);

    Command convertMessageToCommand(WebSocketMessage message) throws MessageConvertionFailed, CommandException;

    WebSocketMessage createMessageFromObjectStates(Collection<GameObjectState> states, GameStateFilter filter) throws MessageConvertionFailed;

}
