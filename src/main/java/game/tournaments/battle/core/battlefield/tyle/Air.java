package game.tournaments.battle.core.battlefield.tyle;

/**
 * Created by maks on 14.10.17.
 */
public class Air extends TyleType {

    private static Air INSTANCE;

    private Air(){

    }

    public static Air instance(){
        if (INSTANCE == null){
            synchronized (Air.class){
                if (INSTANCE == null)
                    INSTANCE = new Air();
            }
        }

        return INSTANCE;
    }

}
