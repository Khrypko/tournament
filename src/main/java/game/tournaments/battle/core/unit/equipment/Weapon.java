package game.tournaments.battle.core.unit.equipment;

import static game.tournaments.battle.core.unit.equipment.EquipmentStatsConstant.*;

import java.util.Map;
import java.util.Set;

/**
 * Created by maks on 24.09.17.
 */
public class Weapon extends Equipment {

    private WeaponClass weaponClass;
    private WeaponType type;

    public Weapon(long id, Set<EquipmentType> equipmentTypes, Map<String, Integer> equipmentStats, EquipmentMaterial material, int durability, WeaponClass weaponClass, WeaponType type) {
        super(id, equipmentTypes, equipmentStats, material, durability);
        this.weaponClass = weaponClass;
        this.type = type;
    }

    public WeaponClass getWeaponClass() {
        return weaponClass;
    }

    public WeaponType getType() {
        return type;
    }

    public int getReach(){
        return this.getEquipmentStats().get(REACH);
    }
}
