package game.tournaments.communication;

import game.tournaments.battle.BattleController;
import game.tournaments.battle.core.command.exception.CommandException;
import game.tournaments.convertor.MessageConvertor;
import game.tournaments.battle.core.command.Command;
import game.tournaments.battle.core.filter.GameStateFilter;
import game.tournaments.battle.core.state.GameObjectState;
import game.tournaments.battle.exception.MessageConvertionFailed;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

import static org.springframework.web.socket.CloseStatus.*;

/**
 * Created by maks on 28.09.17.
 */
public class CommunicatorImpl implements Communicator {

    private ConcurrentLinkedQueue<Command> commands = new ConcurrentLinkedQueue<>();
    private Map<Long,WebSocketSession> sessions = new HashMap<>();

    private MessageConvertor convertor;
    private BattleController controller;

    public CommunicatorImpl(MessageConvertor convertor) {
        this.convertor = convertor;
    }

    @Override
    public void initialize(BattleController controller) {
        this.controller = controller;
    }

    @Override
    public Stream<Command> getMessages() {
        List<Command> newCommands = getCommands();
        return newCommands.stream();
    }

    private List<Command> getCommands() {
        List<Command> newCommands = new ArrayList<>();
        Iterator<Command> iterator = commands.iterator();
        while (iterator.hasNext()){
            newCommands.add(iterator.next());
            iterator.remove();
        }

        return newCommands;
    }

    @Override
    public void appendMessage(WebSocketMessage message) {
        try {
            commands.add(convertor.convertMessageToCommand(message));
        } catch (MessageConvertionFailed messageConvertionFailed) {
            messageConvertionFailed.printStackTrace();
        } catch (CommandException e) {
            // nothing for now...
        }
    }

    @Override
    public void sendMessages(Collection<GameObjectState> gameObjectStates, Map<Long, GameStateFilter> playersFilter) {

        sessions.entrySet().stream()
                .forEach(entry -> sendMessage(entry.getValue(), gameObjectStates, playersFilter.get(entry.getKey())));

    }

    @Override
    public void closeConnections() {
        sessions.values()
                .stream()
                .forEach(session -> {
                try {
                    session.close(NORMAL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        });
    }

    private void sendMessage(WebSocketSession session, Collection<GameObjectState> gameObjectStates, GameStateFilter filter) {
        try {
            if (session.isOpen()){
                WebSocketMessage message = convertor.createMessageFromObjectStates(gameObjectStates, filter);
                session.sendMessage(message);
            }
        } catch (IOException | MessageConvertionFailed e) {
            //TODO
            e.printStackTrace();
        }
    }

    public void registerSession(Long playerId, WebSocketSession webSocketSession){
        this.sessions.put(playerId, webSocketSession);
    }

    @Override
    public void removeSession(Long playerId) {
        this.sessions.remove(playerId);
        if (sessions.isEmpty())
            controller.stopBattle();
    }



}
