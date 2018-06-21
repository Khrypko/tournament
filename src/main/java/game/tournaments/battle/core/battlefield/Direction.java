package game.tournaments.battle.core.battlefield;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by maks on 24.09.17.
 */
public enum Direction {

    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    public boolean isFlankingPosition(Direction direction){
        int indexDifference = direction.ordinal() - this.ordinal();
        if (indexDifference < 0)
            indexDifference *= -1;

        return !(indexDifference == 1 || indexDifference == 7);

    }

    public static Optional<Direction> getDirectionByIndex(int index){
        return Arrays.stream(values())
                .filter(direction -> direction.ordinal() == index)
                .findAny();
    }

}
