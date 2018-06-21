package game.tournaments.battle.core.unit.equipment;

/**
 * Created by maks on 24.09.17.
 */
public class EquipmentMaterial {

    private long id;
    private float resistanceMultiplier;

    public EquipmentMaterial(long id, float resistanceMultiplier) {
        this.id = id;
        this.resistanceMultiplier = resistanceMultiplier;
    }

    public long getId() {
        return id;
    }

    public float getResistanceMultiplier() {
        return resistanceMultiplier;
    }
}
