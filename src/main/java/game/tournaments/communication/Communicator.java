package game.tournaments.communication;

import game.tournaments.battle.BattleController;
import game.tournaments.battle.core.command.Command;
import game.tournaments.battle.core.filter.GameStateFilter;
import game.tournaments.battle.core.state.GameObjectState;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by maks on 24.09.17.
 */
public interface Communicator {

    void initialize(BattleController controller);

    Stream<Command> getMessages();
    void appendMessage(WebSocketMessage message);
    void sendMessages(Collection<GameObjectState> gameObjectStates, Map<Long, GameStateFilter> playersFilter);

    void closeConnections();

    public void registerSession(Long playerId, WebSocketSession webSocketSession);
    public void removeSession(Long playerId);

}
