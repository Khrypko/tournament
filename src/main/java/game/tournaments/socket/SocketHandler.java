package game.tournaments.socket;

import game.tournaments.battle.BattleController;
import game.tournaments.communication.Communicator;
import game.tournaments.core.MainController;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

import static game.tournaments.battle.Constants.*;

/**
 * Created by maks on 25.09.17.
 */
public class SocketHandler extends TextWebSocketHandler{

    private final Logger LOGGER = Logger.getLogger(SocketHandler.class.getName());

    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public SocketHandler() {
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        Optional<Communicator> communicator = getCommunicatorFromSession(session);
        communicator.ifPresent( comm -> comm.appendMessage(message));
    }

    private Optional<Communicator> getCommunicatorFromSession(WebSocketSession session) {
        try {
            return Optional.ofNullable((Communicator) session.getAttributes().get(COMMUNICATOR));
        } catch (ClassCastException e){
            return Optional.empty();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        Optional<Communicator> communicator = getCommunicatorFromSession(session);
        if (!communicator.isPresent()){
            closeSession(session);
            return;
        }

        registerSession(communicator.get(), session);

    }

    private void registerSession(Communicator communicator, WebSocketSession session) {
        Optional<Long> playerId = getPlayerIdFromSession(session);
        if (playerId.isPresent()){
            communicator.registerSession(playerId.get(), session);
            sessions.add(session);
        } else
            closeSession(session);

    }

    private void closeSession(WebSocketSession session) {
        try {
            session.close();
            LOGGER.info("Session " + session.getId() + " is closed");
        } catch (IOException e) {
            LOGGER.info("Exception while closing session. Reason: " + e.getMessage());
        }
    }

    private Optional<Long> getPlayerIdFromSession(WebSocketSession session) {
        try {
            return Optional.ofNullable((Long) session.getAttributes().get(PLAYER_ID));
        } catch (ClassCastException e){
            return Optional.empty();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Communicator communicator = (Communicator) session.getAttributes().get(COMMUNICATOR);
        Long playerId = (Long) session.getAttributes().get(PLAYER_ID);

        communicator.removeSession(playerId);

    }
}
