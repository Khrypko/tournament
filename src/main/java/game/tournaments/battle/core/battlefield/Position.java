package game.tournaments.battle.core.battlefield;

/**
 * Created by maks on 24.09.17.
 */
public class Position {

    private int xPosition;
    private int yPosition;
    private int zPosition;

    public Position(int xPosition, int yPosition, int zPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.zPosition = zPosition;
    }

    public int getX() {
        return xPosition;
    }

    public int getY() {
        return yPosition;
    }

    public int getZ() {
        return zPosition;
    }


    public void updatePosition(int x, int y, int z, Direction direction) {
        this.xPosition = x;
        this.yPosition = y;
        this.zPosition = z;
    }

    public boolean nextTo(Position position, int reach){
        if (!checkNearOnAxis(xPosition, position.getX(), reach))
            return false;
        if (!checkNearOnAxis(yPosition, position.getY(), reach))
            return false;
        if (!checkNearOnAxis(zPosition, position.getZ(), reach))
            return false;

        return true;
    }

    private boolean checkNearOnAxis(int a, int b, int reach) {
        int delta = a - b;
        if (delta > -reach && delta < reach)
            return true;

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (xPosition != position.xPosition) return false;
        if (yPosition != position.yPosition) return false;
        if (zPosition != position.zPosition) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = xPosition;
        result = 31 * result + yPosition;
        result = 31 * result + zPosition;
        return result;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + xPosition +
                ", y=" + yPosition +
                ", z=" + zPosition +
                '}';
    }
}
