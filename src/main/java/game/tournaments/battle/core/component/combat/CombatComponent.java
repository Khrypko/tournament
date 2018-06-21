package game.tournaments.battle.core.component.combat;

import game.tournaments.battle.core.battlefield.Position;
import game.tournaments.battle.core.component.Component;
import game.tournaments.battle.core.system.FightingEngineSystem;
import game.tournaments.battle.core.unit.Squad;
import static game.tournaments.battle.core.component.ComponentConstants.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by maks on 24.09.17.
 */
public class CombatComponent extends Component {

    private FightingEngineSystem fightingSystem;
    private Squad squad;
    private List<FightingComponent> opponents;
    private List<FightingComponent> fighters;

    public CombatComponent(Squad squad, List<FightingComponent> opponents, List<FightingComponent> fighters, FightingEngineSystem fightingSystem) {
        super(COMBAT_COMPONENT);
        this.squad = squad;
        this.opponents = opponents;
        this.fighters = fighters;
        this.fightingSystem = fightingSystem;
    }

    public Squad getSquad() {
        return squad;
    }

    public List<FightingComponent> getOpponents() {
        return opponents;
    }

    public List<FightingComponent> getFighters() {
        return fighters;
    }

    public void addOpponent(FightingComponent opponent){
        this.opponents.add(opponent);
    }

    public void removeOpponent(FightingComponent opponent){
        this.opponents.remove(opponent);
    }

    public Optional<FightingComponent> getClosestOpponent(FightingComponent fighter){
        Position fighterPosition = fighter.getUnitPosition();

        return opponents.stream()
                .filter(opponent -> {
                    Position opponentPosition = opponent.getUnitPosition();
                    return opponentPosition.nextTo(fighterPosition, fighter.getWeaponReach());
                }).findFirst();
    }
}
