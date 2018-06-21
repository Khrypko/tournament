package game.tournaments.battle.core.component.combat;
import game.tournaments.battle.core.GameObject;
import game.tournaments.battle.core.battlefield.Position;
import game.tournaments.battle.core.component.Component;
import game.tournaments.battle.core.component.InventoryComponent;
import game.tournaments.battle.core.system.transform.TransformComponent;
import game.tournaments.battle.core.component.UnitComponent;
import game.tournaments.battle.core.system.FightingEngineSystem;
import game.tournaments.battle.core.unit.Stat;

import java.util.Collections;
import java.util.Optional;

import static game.tournaments.battle.core.component.ComponentConstants.*;


/**
 * Created by maks on 24.09.17.
 */
public class FightingComponent extends Component {

    private GameObject unit;
    private FightingEngineSystem fightingSystem;

    public FightingComponent(GameObject unit, FightingEngineSystem fightingSystem) {
        super(FIGHTING_COMPONENT);
        this.unit = unit;
        this.fightingSystem = fightingSystem;
    }

    public GameObject getUnit() {
        return unit;
    }

    public Position getUnitPosition(){
        TransformComponent transform = (TransformComponent) unit.getComponent(TRANSFORM_COMPONENT);
        return transform.getPosition();
    }

    public int getUnitInitiative(){
        UnitComponent unitComponent = (UnitComponent) unit.getComponent(UNIT_COMPONENT);
        return unitComponent.getStats().getStatValue(Stat.INITIATIVE);
    }

    public void fight(CombatComponent combat){
        Optional<FightingComponent> possibleOpponent = combat.getClosestOpponent(this);
        if (!possibleOpponent.isPresent()){
            consolidate();
            return;
        }

        FightingComponent opponent = possibleOpponent.get();
        Damage damage = calculateDamage();
        opponent.dealDamage(damage);

    }

    private Damage calculateDamage() {
        //TODO
        return new Damage(DamageType.CUTTING, 10, Collections.EMPTY_MAP);
    }

    private void consolidate() {
        //TODO
    }

    public void dealDamage(Damage damage){
        //TODO
    }

    public int getWeaponReach(){
        InventoryComponent inventory = (InventoryComponent) unit.getComponent(INVENTORY_COMPONENT);
        return inventory.getPrimaryWeapon().getReach();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FightingComponent that = (FightingComponent) o;

        if (!unit.equals(that.unit)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return unit.hashCode();
    }
}
