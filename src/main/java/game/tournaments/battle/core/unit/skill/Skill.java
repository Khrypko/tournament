package game.tournaments.battle.core.unit.skill;

import game.tournaments.battle.core.GameObject;
import game.tournaments.battle.core.battlefield.Battlefield;
import game.tournaments.battle.core.command.Command;

/**
 * Created by maks on 24.09.17.
 * Abstract class for unit skills
 */
public abstract class Skill {

    private long id;

    public abstract void use(GameObject user, Command command, Battlefield battlefield);

}
