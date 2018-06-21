package game.tournaments.battle.core.unit;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by maks on 24.09.17.
 * Contains initial and current stats of the unit and operations with them.
 * Like strength, dexterity...
 */
public class Stats {

    private EnumMap<Stat, Integer> initialStats = new EnumMap<Stat, Integer>(Stat.class);
    private EnumMap<Stat, Integer> currentStats = new EnumMap<Stat, Integer>(Stat.class);

    public Stats(Map<Stat, Integer> initialStats) {
        this.initialStats.putAll(initialStats);
        this.currentStats.putAll(initialStats);
    }

    public Integer getStatValue(Stat stat){
        return currentStats.getOrDefault(stat, 0);
    }

    public void setStatValue(Stat stat, Integer value){
        this.currentStats.put(stat, value);
    }

    public void resetStatValue(Stat stat){
        this.currentStats.put(stat, initialStats.get(stat));
    }

    public void resetAllStats(){
        this.currentStats.putAll(initialStats);
    }

}
