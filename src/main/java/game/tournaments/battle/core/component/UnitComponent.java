package game.tournaments.battle.core.component;

import game.tournaments.battle.core.unit.Squad;
import game.tournaments.battle.core.unit.Stats;
import game.tournaments.battle.core.unit.Status;
import game.tournaments.battle.core.unit.Trait;
import game.tournaments.battle.core.unit.skill.Skill;
import game.tournaments.battle.core.unit.skill.SkillDetails;
import static game.tournaments.battle.core.component.ComponentConstants.*;

import java.util.Map;

/**
 * Created by maks on 24.09.17.
 */
public class UnitComponent extends Component{

    private long unitId;
    private int health;
    private int stamina;

    private Status status = Status.STANDING;

    private Squad squad;
    private Stats stats;
    private Map<Long, Trait> traits;
    private Map<Skill, SkillDetails> skills;

    public UnitComponent(long unitId, int health, int stamina, Squad squad, Stats stats, Map<Long, Trait> traits, Map<Skill, SkillDetails> skills) {
        super(UNIT_COMPONENT);
        this.unitId = unitId;
        this.health = health;
        this.stamina = stamina;
        this.squad = squad;
        this.stats = stats;
        this.traits = traits;
        this.skills = skills;
    }

    public long getUnitId() {
        return unitId;
    }

    public int getHealth() {
        return health;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Squad getSquad() {
        return squad;
    }

    public Stats getStats() {
        return stats;
    }

    public Map<Long, Trait> getTraits() {
        return traits;
    }

    public Map<Skill, SkillDetails> getSkills() {
        return skills;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
