package game.tournaments.battle.core.system;

import game.tournaments.battle.core.component.Component;
import game.tournaments.battle.core.component.combat.CombatComponent;
import game.tournaments.battle.core.component.combat.FightingComponent;

import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by maks on 24.09.17.
 */
public class FightingEngineSystem implements EngineSystem {

    private int priority;
    private TreeMap<FightingComponent, CombatComponent> activeFights;

    public FightingEngineSystem(int priority) {
        this.priority = priority;
        Comparator<FightingComponent> comparator = (fc1, fc2) -> fc1.getUnitInitiative() - fc2.getUnitInitiative();
        activeFights = new TreeMap<>(comparator);
    }


    @Override
    public void execute() {
        activeFights.entrySet()
                .stream()
                .forEach(entry -> {
                    FightingComponent fighter = entry.getKey();
                    fighter.fight(entry.getValue());
                });
    }

    @Override
    public void addComponent(Component component) {

    }

    @Override
    public void removeComponent(Component component) {

    }

    @Override
    public int getPriority() {
        return priority;
    }

    public void removeFightingComponentFromActiveFights(FightingComponent fightingComponent){
        this.activeFights.remove(fightingComponent);
    }

    public void addFightingComponents(CombatComponent combatComponent, List<FightingComponent> fightingComponents){
        //TODO
    }
}
