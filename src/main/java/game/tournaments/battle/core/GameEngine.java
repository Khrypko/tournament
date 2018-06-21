package game.tournaments.battle.core;

import game.tournaments.battle.BattleController;
import game.tournaments.battle.core.battlefield.Battlefield;
import game.tournaments.battle.core.command.Command;
import game.tournaments.battle.core.state.GameObjectState;
import game.tournaments.battle.core.system.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by maks on 30.09.17.
 */
public class GameEngine {

    private BattleController controller;
    private CommandProcessor commandProcessor;
    private Battlefield battlefield;

    private Map<Integer, EngineSystem> systems = new TreeMap<>();

    private Map<Long, GameObjectState> turnUpdatedStates;

    public void simulateTurn(Stream<Command> commandStream){

        commandProcessor.process(commandStream);
        clearPreviousStates();
        runAllSystems();

        if (battleShouldBeOver())
            controller.stopBattle();

    }



    private void clearPreviousStates() {
        this.turnUpdatedStates = new HashMap<>();
    }

    private void runAllSystems(){
        systems.values().stream().forEach(EngineSystem::execute);
    }

    public GameObjectState getGameObjectState(long id){
        GameObjectState state = turnUpdatedStates.get(id);
        if (state == null) {
            state = new GameObjectState(id);
            turnUpdatedStates.put(id, state);
        }

        return state;
    }

    public List<GameObjectState> updatedStates(){
        if (turnUpdatedStates == null || turnUpdatedStates.isEmpty())
            return Collections.emptyList();
        return turnUpdatedStates.values().stream().collect(Collectors.toList());
    }

    private boolean battleShouldBeOver() {
        boolean stopGame = checkAnyPlayersSurrendered();
        if (stopGame)
            return true;

        //TODO
        return false;
    }

    private boolean checkAnyPlayersSurrendered() {
        return controller.getPlayers().stream().anyMatch(Player::surrendering);
    }

    public void initialize(BattleController battleController,
                           List<EngineSystem> systems,
                           Battlefield battlefield,
                           CommandProcessor processor){
        this.controller = battleController;
        this.battlefield = battlefield;
        this.commandProcessor = processor;
        systems.stream()
                .forEach(system -> this.systems.put(system.getPriority(), system));
    }
}
