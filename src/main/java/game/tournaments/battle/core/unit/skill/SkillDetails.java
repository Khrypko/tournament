package game.tournaments.battle.core.unit.skill;

import java.util.Map;

/**
 * Created by maks on 24.09.17.
 */
public class SkillDetails {

    private Map<String, String> skillDetails;

    public SkillDetails(Map<String, String> skillDetails) {
        this.skillDetails = skillDetails;
    }

    public String getDetail(String key){
        return skillDetails.get(key);
    }

    public void setDetail(String key, String detail){
        this.skillDetails.put(key, detail);
    }
}
