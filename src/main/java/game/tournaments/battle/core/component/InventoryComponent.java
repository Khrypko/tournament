package game.tournaments.battle.core.component;

import game.tournaments.battle.core.unit.equipment.Armour;
import game.tournaments.battle.core.unit.equipment.ArmourPlace;
import game.tournaments.battle.core.unit.equipment.Weapon;
import static game.tournaments.battle.core.component.ComponentConstants.*;


import java.util.Map;
import java.util.Set;

/**
 * Created by maks on 24.09.17.
 */
public class InventoryComponent extends Component {

    private Map<ArmourPlace, Armour> armour;
    private Set<Weapon> weapons;

    private Weapon primaryWeapon;
    private Weapon secondaryWeapon;

    public InventoryComponent(Map<ArmourPlace, Armour> armour, Set<Weapon> weapons, Weapon primary, Weapon secondary) {
        super(INVENTORY_COMPONENT);
        this.armour = armour;
        this.weapons = weapons;
        this.primaryWeapon = primary;
        this.secondaryWeapon = secondary;
    }

    public Map<ArmourPlace, Armour> getArmour() {
        return armour;
    }

    public Set<Weapon> getWeapons() {
        return weapons;
    }

    public Weapon getPrimaryWeapon() {
        return primaryWeapon;
    }

    public Weapon getSecondaryWeapon() {
        return secondaryWeapon;
    }

    public void switchWeapon(){
        Weapon temp = primaryWeapon;
        primaryWeapon = secondaryWeapon;
        secondaryWeapon = temp;
    }
}
