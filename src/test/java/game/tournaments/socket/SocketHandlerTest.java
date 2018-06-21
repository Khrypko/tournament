package game.tournaments.socket;

import game.tournaments.communication.Communicator;
import game.tournaments.core.MainController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static game.tournaments.battle.Constants.COMMUNICATOR;
import static game.tournaments.battle.Constants.PLAYER_ID;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SocketHandlerTest {

    @Mock private Communicator communicator;
    @Mock private WebSocketSession session;

    private SocketHandler handler;

    private Map<String, Object> attributes;
    private TextMessage message = new TextMessage("dummy");

    @Before
    public void initialize(){
        doNothing().when(communicator).appendMessage(anyObject());
        handler = new SocketHandler();
        attributes = new HashMap<>();
    }

    @Test
    public void testHandleMessageSuccessful() throws IOException, InterruptedException {

        attributes.put(COMMUNICATOR, communicator);
        when(session.getAttributes()).thenReturn(attributes);

        handler.handleTextMessage(session, message);

        verify(communicator).appendMessage(message);
    }

    @Test
    public void testHandleMethodIfAttributesContainsCommunicatorOfWrongType() throws IOException, InterruptedException {

        attributes.put(COMMUNICATOR, new Object());
        when(session.getAttributes()).thenReturn(attributes);

        handler.handleTextMessage(session, message);

        verify(communicator, never()).appendMessage(message);
    }

    @Test
    public void testHandleMethodWhenCommunicatorNotInAttributes() throws IOException, InterruptedException {
        when(session.getAttributes()).thenReturn(Collections.emptyMap());

        handler.handleTextMessage(session, message);

        verify(communicator, never()).appendMessage(message);
    }


    @Test
    public void testAfterConnectionEstablishedWhenCommunicatorNotInAttributes() throws Exception {
        when(session.getAttributes()).thenReturn(Collections.emptyMap());
        doNothing().when(session).close();

        handler.afterConnectionEstablished(session);

        verify(communicator, never()).registerSession(anyLong(), eq(session));
        verify(session).close();

    }

    @Test
    public void testAfterConnectionEstablishedWhenUserIdNotInAttributes() throws Exception {
        when(session.getAttributes()).thenReturn(attributes);
        attributes.put(COMMUNICATOR, communicator);
        doNothing().when(session).close();

        handler.afterConnectionEstablished(session);

        verify(communicator, never()).registerSession(anyLong(), eq(session));
        verify(session).close();

    }

    @Test
    public void testAfterConnectionEstablishedRegisteredSessionSuccessfully() throws Exception {
        when(session.getAttributes()).thenReturn(attributes);
        attributes.put(COMMUNICATOR, communicator);
        attributes.put(PLAYER_ID, 1l);
        doNothing().when(session).close();

        handler.afterConnectionEstablished(session);

        verify(communicator).registerSession(anyLong(), eq(session));

    }

    @Test
    public void testAfterConnectionClosedSuccessfull() throws Exception {
        when(session.getAttributes()).thenReturn(attributes);
        attributes.put(COMMUNICATOR, communicator);
        attributes.put(PLAYER_ID, 1l);

        handler.afterConnectionClosed(session, null);

        verify(communicator).removeSession(1l);

    }
}