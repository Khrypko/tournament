package game.tournaments.socket;

import game.tournaments.battle.BattleController;
import game.tournaments.battle.core.Player;
import game.tournaments.communication.Communicator;
import game.tournaments.core.MainController;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

import static game.tournaments.battle.Constants.*;

/**
 * Created by maks on 28.09.17.
 */
public class WebSocketHandshakeChecker implements HandshakeInterceptor {

    private MainController controller;

    public WebSocketHandshakeChecker(MainController controller) {
        this.controller = controller;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        HttpServletRequest request = ((ServletServerHttpRequest)serverHttpRequest).getServletRequest();
        String battleId = request.getParameter("battleId");
        String playerToken = request.getParameter("token");

        if (battleId == null || playerToken == null)
            return false;

        Optional<BattleController> battle = controller.getBattleController(battleId);
        if (!battle.isPresent())
            return false;

        Optional<Player> possiblePlayer = battle.get().joinBattle(playerToken);
        if (!possiblePlayer.isPresent()){
            return false;
        }

        Player player = possiblePlayer.get();
        Communicator communicator = battle.get().getCommunicator();

        map.put(PLAYER_ID, player.getId());
        map.put(COMMUNICATOR, communicator);
        return true;

    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
