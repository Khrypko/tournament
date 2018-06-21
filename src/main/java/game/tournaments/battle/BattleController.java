package game.tournaments.battle;

import game.tournaments.battle.config.BattleConfiguration;
import game.tournaments.battle.core.Player;
import game.tournaments.battle.core.filter.GameStateFilter;
import game.tournaments.battle.core.GameEngine;
import game.tournaments.battle.core.state.GameObjectState;
import game.tournaments.battle.exception.BattleCreationException;
import game.tournaments.communication.Communicator;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by maks on 24.09.17.
 */
public class BattleController implements Runnable {

    private final Logger LOGGER = Logger.getLogger(BattleController.class.getName());
    private static final int TURNS_PER_SECOND = 20;
    private static final int TIME_BETWEEN_TURNS = 1000 / TURNS_PER_SECOND;

    private String battleId;

    private BattleLifecycle battleLifecycle;
    private Communicator communicator;
    private GameEngine gameEngine;
    private Map<Long, Player> players;
    private boolean active = false;

    private BattleController(String battleId,
                            BattleLifecycle battleLifecycle,
                            Map<Long, Player> players,
                            Communicator communicator,
                            GameEngine gameEngine) {
        this.battleId = battleId;
        this.battleLifecycle = battleLifecycle;
        this.players = players;
        this.communicator = communicator;
        this.gameEngine = gameEngine;
    }

    @Override
    public void run() {
        active = true;
        runGameLoop();
        endBattle();
    }

    //TODO
    private void endBattle() {
        sendMessagesToPlayers();
        battleLifecycle.endBattle(createBattleResults());
    }

    private void sendMessagesToPlayers() {
        //TODO
    }

    private void runGameLoop() {
        //tps measurment
        int tps = 0;
        long timePassedSinceLastSecond = 0;
        long lastTurnTime;

        long nextTurnTime = System.currentTimeMillis();
        while (active){

            //tps
            lastTurnTime = nextTurnTime;

            gameEngine.simulateTurn(communicator.getMessages());
            notifyPlayers();


            nextTurnTime += TIME_BETWEEN_TURNS;
            long sleepTime = nextTurnTime - System.currentTimeMillis();
            if (sleepTime >= 0){
                sleep(sleepTime);
            }

            //tps measurment
            timePassedSinceLastSecond += System.currentTimeMillis() - lastTurnTime;
            tps++;

            //tps measurment
            if (timePassedSinceLastSecond >= 1000){
                System.out.println(timePassedSinceLastSecond);
                System.out.println("TPS: " + tps);
                tps = 0;
                timePassedSinceLastSecond = 0;
            }
        }
    }

    private void sleep(long timeToSleep) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeToSleep);
            //Thread.sleep(timeToSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void notifyPlayers() {
        List<GameObjectState> updatedStates = gameEngine.updatedStates();
        if (!updatedStates.isEmpty())
            communicator.sendMessages(updatedStates, getPlayerFilters());

    }

    //TODO
    private Map<Long, GameStateFilter> getPlayerFilters() {
        return Collections.emptyMap();
    }

    //TODO
    private BattleResults createBattleResults() {
        return null;
    }

    public String getBattleId() {
        return battleId;
    }

    public boolean isActive() {
        return active;
    }

    public Communicator getCommunicator() {
        return communicator;
    }

    public List<Player> getPlayers(){
        return players.values().stream().collect(Collectors.toList());
    }

    public Map<Long, String> getPlayerTokens(){
        return players.values().stream()
                .collect(Collectors.toMap(Player::getId, Player::getToken));
    }

    public Optional<Player> joinBattle(String token){
        Optional<Player> player = players.values().stream().filter(p -> p.getToken().equals(token)).findFirst();
        if (!player.isPresent())
            return Optional.ofNullable(null);

        player.get().setJoined(true);

        if (!active)
            checkAndSetAllPlayersAreJoined();

        return Optional.ofNullable(player.get());
    }

    private void checkAndSetAllPlayersAreJoined() {
        if (allPlayersJoined())
            startBattle();
    }

    public boolean allPlayersJoined(){
        return !players.values().stream().anyMatch(p -> !p.isJoined());
    }

    public void stopBattle(){
        this.active = false;
    }

    private void startBattle() {
        battleLifecycle.startBattle(this);
    }

    public static BattleController createBattle(BattleConfiguration configuration) throws BattleCreationException {
        if (configuration.isComplete()){
            BattleController controller = new BattleController(
                    configuration.getBattleId(),
                    configuration.getBattleLifecycle(),
                    configuration.getPlayers(),
                    configuration.getCommunicator(),
                    configuration.getGameEngine()
            );

            configuration.getGameEngine().initialize(controller,
                    configuration.getSystems(),
                    configuration.getBattlefield(),
                    configuration.getCommandProcessor());

            configuration.getCommunicator().initialize(controller);

            return controller;
        }

        throw new BattleCreationException("Incomplete configuration");
    }
}
