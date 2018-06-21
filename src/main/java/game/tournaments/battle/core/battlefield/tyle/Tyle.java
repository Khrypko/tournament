package game.tournaments.battle.core.battlefield.tyle;

import game.tournaments.battle.core.GameObject;

/**
 * Created by maks on 13.10.17.
 */
public class Tyle {

    private GameObject gameObject;
    private TyleType tyleType;

    public Tyle(TyleType tyleType) {
        this.tyleType = tyleType;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public TyleType getTyleType() {
        return tyleType;
    }

    public void setTyleType(TyleType tyleType) {
        this.tyleType = tyleType;
    }
}
