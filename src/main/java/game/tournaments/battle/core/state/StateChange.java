package game.tournaments.battle.core.state;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maks on 01.10.17.
 */
public class StateChange {

//    private ChangeType changeType;
//    private Map<String, Object> parameters;

    private Map<ChangeType, Map<String, Object>> changes = new HashMap<>();

    //private int animationId ????

    public Map<ChangeType, Map<String, Object>> getChanges() {
        return changes;
    }

    public void setChanges(Map<ChangeType, Map<String, Object>> changes) {
        this.changes = changes;
    }

    public void addChange(ChangeType type, String key, Object value){
        Map<String, Object> changeMap = getOrCreateChangeMap(type);
        changeMap.put(key, value);
    }

    private Map<String, Object> getOrCreateChangeMap(ChangeType type) {
        Map<String, Object> map = changes.get(type);
        if (map == null){
            map = new HashMap<>();
            changes.put(type, map);
        }

        return map;
    }
}
