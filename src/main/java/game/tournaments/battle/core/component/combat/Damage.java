package game.tournaments.battle.core.component.combat;

import java.util.Map;

/**
 * Created by maks on 24.09.17.
 */
public class Damage {

    public DamageType type;
    public int amount;
    public Map<String, String> additionalDetails;

    public Damage(DamageType type, int amount, Map<String, String> additionalDetails) {
        this.type = type;
        this.amount = amount;
        this.additionalDetails = additionalDetails;
    }
}
