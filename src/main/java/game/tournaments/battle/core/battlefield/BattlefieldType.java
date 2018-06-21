package game.tournaments.battle.core.battlefield;

/**
 * Created by maks on 13.10.17.
 */
public enum BattlefieldType {

    TEST(20, 20, 6);

    private int length;
    private int width;
    private int height;

    BattlefieldType(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
