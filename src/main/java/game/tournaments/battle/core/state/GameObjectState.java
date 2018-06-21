package game.tournaments.battle.core.state;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maks on 30.09.17.
 */
public class GameObjectState {

    private long id;

    //map of changes by component name
    private Map<String, StateChange> changes = new HashMap<>();

    public GameObjectState() {
    }

    public GameObjectState(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<String, StateChange> getChanges() {
        return changes;
    }

    public void setChanges(Map<String, StateChange> changes) {
        this.changes = changes;
    }

    public StateChange getOrCreateStateChange(String componentType){
        StateChange change = changes.get(componentType);
        if (change == null){
            change = new StateChange();
            changes.put(componentType, change);
        }

        return change;
    }
}
