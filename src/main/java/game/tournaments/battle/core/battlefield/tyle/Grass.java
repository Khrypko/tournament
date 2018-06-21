package game.tournaments.battle.core.battlefield.tyle;

/**
 * Created by maks on 14.10.17.
 */
public class Grass extends TyleType {

    private static Grass INSTANCE;

    private Grass(){

    }

    public static Grass instance(){
        if (INSTANCE == null){
            synchronized (Air.class){
                if (INSTANCE == null)
                    INSTANCE = new Grass();
            }
        }

        return INSTANCE;
    }

}
