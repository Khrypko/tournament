package game.tournaments.config;

import game.tournaments.core.MainController;
import game.tournaments.socket.SocketHandler;
import game.tournaments.socket.WebSocketHandshakeChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * Created by maks on 25.09.17.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

    @Autowired
    private MainController mainController;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(getMyTestHandler(), "/test")
                .setAllowedOrigins("*")
                .addInterceptors(getHandshakeInterceptor(mainController));
    }

    @Bean
    public WebSocketHandler getMyTestHandler(){
        return new SocketHandler();
    }

    @Bean
    public HandshakeInterceptor getHandshakeInterceptor(MainController mainController){
        return new WebSocketHandshakeChecker(mainController);
    }
}
