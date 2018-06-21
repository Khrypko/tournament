package game.tournaments.battle.core;

import game.tournaments.battle.core.unit.Squad;

import java.util.Map;

/**
 * Created by maks on 24.09.17.
 */
public class Player {

    private long id;
    private Map<Integer, Squad> squads;
    private String token;
    private boolean joined;
    private boolean surrender = false;

    public Player(long id, Map<Integer, Squad> squads, String token) {
        this.id = id;
        this.squads = squads;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<Integer, Squad> getSquads() {
        return squads;
    }

    public void setSquads(Map<Integer, Squad> squads) {
        this.squads = squads;
    }

    public String getToken() {
        return token;
    }

    public void setJoined(boolean joined) {
        this.joined = joined;
    }

    public boolean isJoined() {
        return joined;
    }

    public void surrender(){
        surrender = true;
    }

    public boolean surrendering() {
        return surrender;
    }
}
