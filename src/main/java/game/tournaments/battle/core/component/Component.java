package game.tournaments.battle.core.component;

/**
 * Created by maks on 24.09.17.
 */
public abstract class Component {

    public String id;

    public Component(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
