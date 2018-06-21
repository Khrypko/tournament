package game.tournaments.battle.core.unit.equipment;

import java.util.Set;

/**
 * Created by maks on 24.09.17.
 */
public class EquipmentType {

    private long id;
    private String name;
    private EquipmentType parent;

    public EquipmentType(long id, String name, EquipmentType parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public EquipmentType getParent() {
        return parent;
    }
}
