package game.tournaments.battle.core.unit;

import game.tournaments.battle.core.GameObject;
import game.tournaments.battle.core.Player;
import game.tournaments.battle.core.component.UnitComponent;
import game.tournaments.battle.core.unit.skill.Skill;
import static game.tournaments.battle.core.component.ComponentConstants.*;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by maks on 24.09.17.
 */
public class Squad {

    private int id;
    private int morale;

    private GameObject commander;
    private Map<Long, GameObject> units;

    private Player player;

    public Squad(int id, int morale, GameObject commander, Map<Long, GameObject> units, Player player) {
        this.id = id;
        this.morale = morale;
        this.commander = commander;
        this.units = units;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getId() {
        return id;
    }

    public int getMorale() {
        return morale;
    }

    public GameObject getCommander() {
        return commander;
    }

    public Map<Long, GameObject> getUnits() {
        return units;
    }

    public Set<Skill> getSquadSkills(){
        return units.values().stream()
                .flatMap(unit -> {
                    UnitComponent unitComponent = (UnitComponent) unit.getComponent(UNIT_COMPONENT);
                    return unitComponent.getSkills().keySet().stream();
                })
                .collect(Collectors.toSet());
    }
}
