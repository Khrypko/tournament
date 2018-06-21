package game.tournaments.battle.core.battlefield;

import game.tournaments.battle.core.GameObject;
import game.tournaments.battle.core.battlefield.tyle.Air;
import game.tournaments.battle.core.battlefield.tyle.Grass;
import game.tournaments.battle.core.battlefield.tyle.TyleType;
import game.tournaments.battle.core.battlefield.tyle.Tyle;
import game.tournaments.battle.core.component.ComponentConstants;
import game.tournaments.battle.core.system.transform.TransformComponent;
import game.tournaments.battle.exception.BattleCreationException;
import game.tournaments.battle.exception.PositionOccupied;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by maks on 24.09.17.
 */
public class Battlefield {

    private int WIDTH;
    private int LENGTH;
    private int HEIGHT;

    private BattlefieldType type;

    private Tyle[][][] battlefield;
    private Map<Long, GameObject> objects;

    public Battlefield() {
        objects = new HashMap<>();
    }

    private Battlefield(int x, int y, int z, BattlefieldType type) {
        objects = new HashMap<>();
    }

    public void initialize(List<GameObject> gameObjects){
        gameObjects.forEach(o -> objects.put(o.getId(), o));
    }
    
    public Optional<GameObject> getObjectById(long id){
        return Optional.ofNullable(objects.get(id));
    }

    public Optional<GameObject> getObjectAtPosition(int x, int y, int z){
        if (!indexesAreOnMap(x, y, z))
            return Optional.ofNullable(null);

        return Optional.ofNullable(battlefield[x][y][z].getGameObject());
    }

    public void setObject(Position position, GameObject object) throws PositionOccupied {
        if (battlefield[position.getX()][position.getY()][position.getY()].getGameObject() != null){
            throw new PositionOccupied();
        }

        objects.putIfAbsent(object.getId(), object);
        battlefield[position.getX()][position.getY()][position.getY()].setGameObject(object);
    }

    public void removeObject(GameObject object){
        try {

            TransformComponent transform = (TransformComponent) object.getComponent(ComponentConstants.TRANSFORM_COMPONENT);
            Position position = transform.getPosition();
            battlefield[position.getX()][position.getY()][position.getZ()].setGameObject(null);
            objects.remove(object.getId());
        } catch (ClassCastException | NullPointerException e){}
    }

    // Smells bad =( Maybe, should be done with recursion... but it's really late, and i want to sleep(((
    public Map<Position, GameObject> getObjectsInRange(Position position, int range){
        if (!indexesAreOnMap(position.getX(), position.getY(), position.getZ()))
            return Collections.emptyMap();

        Map<Position, GameObject> objects = new HashMap<>();

        for (int x = position.getX() - range; x <= position.getX() + range ; x++){
            if (!indexesAreOnMap(x, position.getY(), position.getZ()))
                continue;

            for (int y = position.getY() - range; y <= position.getY() + range; y++){
                if (!indexesAreOnMap(x, y, position.getZ()))
                    continue;

                for (int z = position.getZ() - range; z <= position.getZ() + range; z++){
                    if (!indexesAreOnMap(x, y, z))
                        continue;

                    GameObject object = battlefield[x][y][z].getGameObject();
                    if (object != null)
                        objects.put(new Position(z, y, z), object);
                }
            }
        }

        return objects;
    }

    //TODO
    public Position getNextPositionToMove(Position currentPosition, Position desiredPosition) {
        return desiredPosition;
    }

    public boolean positionIsOnBattlefield(Position position){
        return indexesAreOnMap(position.getX(), position.getY(), position.getZ());
    }

    private boolean indexesAreOnMap(int x, int y, int z) {
        if (x < 0 || x >= battlefield.length)
            return false;

        if (y < 0 || y >= battlefield[x].length)
            return false;

        if (z < 0 || z >= battlefield[x][y].length)
            return false;

        return true;
    }


    public static BattlefieldBuilder builderOfType(BattlefieldType battlefieldType){
        return new BattlefieldBuilder(battlefieldType);
    }

    public static class BattlefieldBuilder {

        private Battlefield battlefield;
        private List<TyleType> possibleTyleTypes;
        private Map<Position, GameObject> objectMap;
        private BattlefieldType battlefieldType;

        private int WIDTH;
        private int LENGTH;
        private int HEIGHT;

        public BattlefieldBuilder(BattlefieldType battlefieldType) {
            this.battlefieldType = battlefieldType;
        }

        public BattlefieldBuilder size(int length, int width, int height) throws BattleCreationException {
            checkValuesValid(Arrays.asList(length, width, height));
            WIDTH = width;
            LENGTH = length;
            HEIGHT = height;

            return this;
        }

        public BattlefieldBuilder possibleTerrain(List<TyleType> tyleTypes){
            possibleTyleTypes = tyleTypes;
            return this;
        }

        public BattlefieldBuilder prePopulate(Map<Position, GameObject> objectMap){
            this.objectMap = objectMap;
            return this;
        }

        public BattlefieldBuilder battlefieldType(BattlefieldType battlefieldType){
            this.battlefieldType = battlefieldType;
            return this;
        }

        public Battlefield build() throws BattleCreationException {

            battlefield = new Battlefield();

            battlefield.battlefield = new Tyle[LENGTH][WIDTH][HEIGHT];
            battlefield.WIDTH = WIDTH;
            battlefield.LENGTH = LENGTH;
            battlefield.HEIGHT = HEIGHT;

            if (battlefieldType == null)
                throw new BattleCreationException("Not specified battlefield type");

            battlefield.type = this.battlefieldType;

            createTerrain();
            populate();

            return battlefield;
        }

        private void populate() {
            objectMap.entrySet()
                    .stream()
                    .forEach(entry -> {
                        Position position = entry.getKey();
                        GameObject object = entry.getValue();
                        battlefield.battlefield[position.getX()][position.getY()][position.getZ()].setGameObject(object);
                        battlefield.objects.put(object.getId(), object);
                    });
        }

        private void createTerrain() {
            IntStream
                    .range(0, battlefield.LENGTH - 1)
                    .forEach( x -> IntStream
                            .range(0, battlefield.WIDTH - 1)
                            .forEach(y -> IntStream
                                    .range(0, battlefield.HEIGHT - 1)
                                    .forEach(z -> setTyle(x, y, z))));
        }

        private void setTyle(int x, int y, int z){
            if (z > 0)
                battlefield.battlefield[x][y][z] = new Tyle(Air.instance());

            battlefield.battlefield[x][y][z] = defineCurrentTyle(battlefield);

        }

        private Tyle defineCurrentTyle(Battlefield battlefield) {
            //TODO really big todo... but for now just return grass typed tyle;

            return new Tyle(Grass.instance());
        }

        private void checkValuesValid(List<Integer> values) throws BattleCreationException {

            if (values
                    .stream()
                    .filter(value -> value > 0)
                    .filter(value -> value % 2 == 0)
                    .count() < values.size()
                    )
                throw new BattleCreationException("Illegal size specified: " + values.toString());
        }

    }

}
