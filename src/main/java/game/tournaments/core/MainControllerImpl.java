package game.tournaments.core;

import game.tournaments.battle.*;
import game.tournaments.battle.config.BattleDetails;
import game.tournaments.battle.config.BattleFactory;
import game.tournaments.battle.config.battlefield.ecxeption.InsufficientDataToCreateBattlefield;
import game.tournaments.battle.exception.BattleCreationException;
import game.tournaments.battle.exception.BattleIsActiveYet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by maks on 28.09.17.
 */
@Component
public class MainControllerImpl implements MainController, BattleLifecycle {

    private TaskExecutor taskExecutor;
    private BattleFactory battleFactory;

    private Map<String, BattleController> activeBattles = new ConcurrentHashMap<>();

    @Autowired
    public MainControllerImpl(TaskExecutor taskExecutor, BattleFactory battleFactory) {
        this.taskExecutor = taskExecutor;
        this.battleFactory = battleFactory;
    }

    @Override
    public BattleController createBattle(BattleDetails configuration) throws BattleCreationException, InsufficientDataToCreateBattlefield {
        checkConfiguration(configuration);

        configuration.setBattleLifecycle(this);
        BattleController battle = battleFactory.createBattle(configuration);
        activeBattles.put(battle.getBattleId(), battle);
        return battle;
    }

    private void checkConfiguration(BattleDetails configuration) throws BattleCreationException {
        if (configuration == null)
            throw new BattleCreationException("Configuration not specified");
    }

    @Override
    public void removeBattle(String id) throws BattleIsActiveYet {
        BattleController battle = activeBattles.get(id);
        if (activeBattles != null){
            if (battle.isActive())
                throw new BattleIsActiveYet();

            activeBattles.remove(id);
        }
    }

    @Override
    public boolean battleExists(String battleId) {
        return activeBattles.containsKey(battleId);
    }

    @Override
    public Optional<BattleController> getBattleController(String id) {
        return Optional.ofNullable(activeBattles.get(id));
    }

    @Override
    public void startBattle(BattleController battleController) {
        if (!activeBattles.containsKey(battleController.getBattleId()))
            activeBattles.put(battleController.getBattleId(), battleController);
        taskExecutor.execute(battleController);
    }

    @Override
    public void endBattle(BattleResults results) {
        //TODO
    }
}
