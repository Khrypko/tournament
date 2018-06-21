package game.tournaments.battle.core.unit.equipment;

import java.util.Map;
import java.util.Set;

/**
 * Created by maks on 24.09.17.
 */
public class Armour extends Equipment {

    private ArmourPlace primaryPlace;
    private Set<ArmourPlace> coveredBodyParts;

    public Armour(long id, Set<EquipmentType> equipmentTypes, Map<String, Integer> equipmentStats, EquipmentMaterial material, int durability, ArmourPlace primaryPlace, Set<ArmourPlace> coveredBodyParts) {
        super(id, equipmentTypes, equipmentStats, material, durability);
        this.primaryPlace = primaryPlace;
        this.coveredBodyParts = coveredBodyParts;
    }

    public ArmourPlace getPrimaryPlace() {
        return primaryPlace;
    }

    public Set<ArmourPlace> getCoveredBodyParts() {
        return coveredBodyParts;
    }

}
