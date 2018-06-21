package game.tournaments.convertor;

import java.util.Optional;

/**
 * Created by maks on 09.10.17.
 */
public class ConverterUtil {

    public static Optional<Long> convertStringToLong(String string){
        Long value = null;
        try {
            value = Long.parseLong(string);
        } catch (NumberFormatException ignored){}

        return Optional.ofNullable(value);
    }

    public static Optional<Integer> convertStringToInteger(String string){
        Integer value = null;
        try {
            value = Integer.parseInt(string);
        } catch (NumberFormatException ignored){}

        return Optional.ofNullable(value);
    }

}
