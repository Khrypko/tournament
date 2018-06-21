package game.tournaments.battle.config;

import game.tournaments.battle.BattleLifecycle;
import game.tournaments.battle.core.CommandProcessor;
import game.tournaments.battle.core.Player;
import game.tournaments.battle.core.GameEngine;
import game.tournaments.battle.core.battlefield.Battlefield;
import game.tournaments.battle.core.system.EngineSystem;
import game.tournaments.communication.Communicator;

import java.util.List;
import java.util.Map;

/**
 * Created by maks on 28.09.17.
 */
public class BattleConfiguration {

    private String battleId;

    private BattleLifecycle battleLifecycle;
    private Communicator communicator;
    private GameEngine gameEngine;
    private List<EngineSystem> systems;
    private CommandProcessor commandProcessor;
    private Battlefield battlefield;

    private Map<Long, Player> players;

    public String getBattleId() {
        return battleId;
    }

    public void setBattleId(String battleId) {
        this.battleId = battleId;
    }

    public BattleLifecycle getBattleLifecycle() {
        return battleLifecycle;
    }

    public void setBattleLifecycle(BattleLifecycle battleLifecycle) {
        this.battleLifecycle = battleLifecycle;
    }

    public Communicator getCommunicator() {
        return communicator;
    }

    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public Map<Long, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<Long, Player> players) {
        this.players = players;
    }

    public List<EngineSystem> getSystems() {
        return systems;
    }

    public void setSystems(List<EngineSystem> systems) {
        this.systems = systems;
    }

    public CommandProcessor getCommandProcessor() {
        return commandProcessor;
    }

    public void setCommandProcessor(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public void setBattlefield(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    public boolean isComplete(){
        if (battleId == null ||
                battleLifecycle == null ||
                communicator == null ||
                gameEngine == null ||
                commandProcessor == null ||
                battlefield == null ||
                systems == null || systems.isEmpty() ||
                players == null || players.size() < 2)
            return false;

        return true;
    }
}
