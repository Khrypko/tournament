package game.tournaments.battle.config.battlefield;

import game.tournaments.battle.config.BattleDetails;
import game.tournaments.battle.config.battlefield.ecxeption.InsufficientDataToCreateBattlefield;
import game.tournaments.battle.core.GameObject;
import game.tournaments.battle.core.battlefield.Battlefield;
import game.tournaments.battle.core.battlefield.BattlefieldType;
import game.tournaments.battle.core.battlefield.Direction;
import game.tournaments.battle.core.battlefield.Position;
import game.tournaments.battle.core.battlefield.tyle.TyleType;
import game.tournaments.battle.core.system.transform.TransformComponent;
import game.tournaments.battle.exception.BattleCreationException;
import game.tournaments.convertor.ConverterUtil;

import javax.naming.InsufficientResourcesException;
import java.util.*;

/**
 * Created by maks on 15.10.17.
 */
public class BattlefieldFactoryImpl implements BattlefieldFactory {

    private final String TYPE = "type";

    @Override
    public Battlefield createBattlefield(BattleDetails details) throws InsufficientDataToCreateBattlefield, BattleCreationException {
        verifyDetails(details);

        Optional<BattlefieldType> possibleType = getBattlefieldType(details.getParameters());
        if (!possibleType.isPresent())
            throw new InsufficientDataToCreateBattlefield("battlefield type not specified");

        BattlefieldType type = possibleType.get();
        Battlefield battlefield = Battlefield.builderOfType(type)
                .size(type.getLength(), type.getWidth(), type.getHeight())
                .possibleTerrain(getTerrainTypes(type))
                .prePopulate(getNonPlayerObjects(type))
                .build();

        return battlefield;
    }

    //TODO
    private Map<Position, GameObject> getNonPlayerObjects(BattlefieldType type) {
        // just for test positioning one object for each player...
        Map<Position, GameObject> objects = new HashMap<>();

        Position firstPosition = new Position(1,0,0);
        GameObject firstPlayerObject = getObjectForPlayer(1l, 1l, firstPosition, Direction.NORTH);
        objects.put(firstPosition, firstPlayerObject);

        Position secondPosition = new Position(1,1,0);
        GameObject secondPlayerObject = getObjectForPlayer(2l, 2l, secondPosition, Direction.NORTH);
        objects.put(secondPosition, secondPlayerObject);

        return objects;
    }

    //TODO remove
    private GameObject getObjectForPlayer(long id, long owner, Position position, Direction direction) {

        GameObject object = new GameObject(id, owner);
        object.addComponent(new TransformComponent(position, direction, object, 1));

        return object;
    }

    //TODO
    private List<TyleType> getTerrainTypes(BattlefieldType type) {
        return Collections.emptyList();
    }

    private Optional<BattlefieldType> getBattlefieldType(Map<String, String> parameters) {
        try {
            return Optional.of(BattlefieldType.valueOf(parameters.get(TYPE)));
        } catch (IllegalArgumentException | NullPointerException e){
            return Optional.empty();
        }
    }

    private void verifyDetails(BattleDetails details) throws InsufficientDataToCreateBattlefield {
        if (details == null)
            throw new InsufficientDataToCreateBattlefield("null details passed");
    }
}
