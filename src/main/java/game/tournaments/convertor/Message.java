package game.tournaments.convertor;

import java.util.Map;

/**
 * Created by maks on 24.09.17.
 */
public class Message {

    private int type;
    private long playerId;
    private Map<String, String> command;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public Map<String, String> getCommand() {
        return command;
    }

    public void setCommand(Map<String, String> command) {
        this.command = command;
    }
}
