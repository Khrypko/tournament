package game.tournaments.battle.config;

import game.tournaments.battle.BattleController;
import game.tournaments.battle.config.battlefield.BattlefieldFactory;
import game.tournaments.battle.config.battlefield.ecxeption.InsufficientDataToCreateBattlefield;
import game.tournaments.battle.core.CommandProcessor;
import game.tournaments.battle.core.GameObject;
import game.tournaments.battle.core.battlefield.Battlefield;
import game.tournaments.battle.core.battlefield.BattlefieldType;
import game.tournaments.battle.core.battlefield.Direction;
import game.tournaments.battle.core.battlefield.Position;
import game.tournaments.battle.core.battlefield.tyle.TyleType;
import game.tournaments.battle.core.command.factory.CommandCreator;
import game.tournaments.battle.core.command.factory.CommandFactory;
import game.tournaments.battle.core.component.Component;
import game.tournaments.battle.core.component.ComponentConstants;
import game.tournaments.battle.core.component.exception.IncorrectComponentType;
import game.tournaments.battle.core.system.EngineSystem;
import game.tournaments.battle.core.system.transform.TransformComponent;
import game.tournaments.battle.core.system.transform.TransformSystem;
import game.tournaments.convertor.TextMessageConverter;
import game.tournaments.battle.core.Player;
import game.tournaments.battle.core.GameEngine;
import game.tournaments.battle.exception.BattleCreationException;
import game.tournaments.communication.Communicator;
import game.tournaments.communication.CommunicatorImpl;
import game.tournaments.convertor.factory.TransformCommandFactory;

import java.util.*;

/**
 * Created by maks on 28.09.17.
 */
public class BattleFactoryImpl implements BattleFactory {

    //TODO implement creation of battlefield, commandprocessor and systems

    private BattlefieldFactory battlefieldFactory;

    public BattleFactoryImpl(BattlefieldFactory battlefieldFactory) {
        this.battlefieldFactory = battlefieldFactory;
    }

    @Override
    public BattleController createBattle(BattleDetails configuration) throws BattleCreationException, InsufficientDataToCreateBattlefield {
        GameEngine gameEngine = setUpBattleEngine();
        BattleConfiguration battleConfiguration = setUpBattleConfiguration(configuration, gameEngine);
        return BattleController.createBattle(battleConfiguration);
    }

    private BattleConfiguration setUpBattleConfiguration(BattleDetails configuration, GameEngine gameEngine) throws BattleCreationException, InsufficientDataToCreateBattlefield {
        BattleConfiguration battleConfiguration = new BattleConfiguration();
        battleConfiguration.setBattleId(createBattleId());
        battleConfiguration.setBattleLifecycle(configuration.getBattleLifecycle());
        battleConfiguration.setBattlefield(setUpBattlefield(configuration));
        battleConfiguration.setCommunicator(createCommunicator(battleConfiguration));
        battleConfiguration.setGameEngine(gameEngine);
        battleConfiguration.setPlayers(getPlayers(configuration.getPlayersIds()));
        battleConfiguration.setCommandProcessor(getCommandProcessor());
        battleConfiguration.setSystems(getSystems(battleConfiguration));

        return battleConfiguration;
    }

    private CommandProcessor getCommandProcessor() {
        return new CommandProcessor();
    }

    //TODO
    private Battlefield setUpBattlefield(BattleDetails configuration) throws BattleCreationException, InsufficientDataToCreateBattlefield {
        return battlefieldFactory.createBattlefield(configuration);
    }


    //TODO
    private List<EngineSystem> getSystems(BattleConfiguration configuration) {
        List<EngineSystem> systems = new LinkedList<>();

        TransformSystem transformSystem = new TransformSystem(1, configuration.getBattlefield(), configuration.getGameEngine());
        systems.add(transformSystem);

        try {
            transformSystem.addComponent(configuration.getBattlefield().getObjectById(1l).get().getComponent(ComponentConstants.TRANSFORM_COMPONENT));
            transformSystem.addComponent(configuration.getBattlefield().getObjectById(2l).get().getComponent(ComponentConstants.TRANSFORM_COMPONENT));
        } catch (IncorrectComponentType incorrectComponentType) {
            incorrectComponentType.printStackTrace();
        }

        return systems;

    }

    //TODO
    private Map<Long, Player> getPlayers(List<Long> playersIds) {
        Map<Long, Player> playerMap = new HashMap<>();
        playerMap.put(1l, new Player(1, null, "111"));
        playerMap.put(2l, new Player(2, null, "222"));

        return playerMap;
    }

    //TODO
    private Communicator createCommunicator(BattleConfiguration configuration) {
        CommandCreator commandCreator = getCommandCreator(configuration);
        return new CommunicatorImpl(new TextMessageConverter(commandCreator));
    }

    private CommandCreator getCommandCreator(BattleConfiguration configuration) {
        Map<Integer, CommandFactory> commandFactories = new HashMap<>();
        commandFactories.put(CommandFactory.TRANSFORM_COMMAND, new TransformCommandFactory(configuration.getBattlefield()));

        return new CommandCreator(commandFactories);
    }

    private String createBattleId() {
        return String.valueOf(System.currentTimeMillis());
    }

    //TODO
    private GameEngine setUpBattleEngine() {
        return new GameEngine();
    }
}
