package game.tournaments.battle.core.unit.equipment;

import java.util.Map;
import java.util.Set;

/**
 * Created by maks on 24.09.17.
 */
public abstract class Equipment {

    private long id;
    private Set<EquipmentType> equipmentTypes;
    private Map<String, Integer> equipmentStats;
    private EquipmentMaterial material;
    private int durability;

    private boolean destroyed = false;

    public Equipment(long id, Set<EquipmentType> equipmentTypes, Map<String, Integer> equipmentStats, EquipmentMaterial material, int durability) {
        this.id = id;
        this.equipmentTypes = equipmentTypes;
        this.equipmentStats = equipmentStats;
        this.material = material;
        this.durability = durability;
    }

    public long getId() {
        return id;
    }

    public EquipmentMaterial getMaterial() {
        return material;
    }

    public Set<EquipmentType> getEquipmentTypes() {
        return equipmentTypes;
    }

    public Map<String, Integer> getEquipmentStats() {
        return equipmentStats;
    }

    public void setEquipmentStat(String statName, Integer statValue){
        this.equipmentStats.put(statName, statValue);
    }

    public int getDurability() {
        return durability;
    }

    public void takeDamage(int damage){
        int newDurability = durability - damage;
        if (newDurability < 0){
            durability = 0;
            destroyed = true;
        } else
            durability = newDurability;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
