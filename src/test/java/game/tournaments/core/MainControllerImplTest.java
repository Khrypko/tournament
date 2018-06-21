package game.tournaments.core;

import game.tournaments.battle.BattleController;
import game.tournaments.battle.config.BattleDetails;
import game.tournaments.battle.config.BattleFactory;
import game.tournaments.battle.exception.BattleCreationException;
import game.tournaments.battle.exception.BattleIsActiveYet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.task.TaskExecutor;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MainControllerImplTest {

    @Mock
    private TaskExecutor taskExecutor;

    @Mock
    private BattleFactory battleFactory;

    @InjectMocks
    private MainControllerImpl controller;

    @Test(expected = BattleCreationException.class)
    public void testCreateBattleThrowsExceptionIfNullConfigurationProvided() throws Exception {
        controller.createBattle(null);
    }

    @Test
    public void testCreateBattle() throws Exception {
        BattleController battleController = mock(BattleController.class);
        when(battleController.getBattleId()).thenReturn("1");
        when(battleFactory.createBattle(anyObject())).thenReturn(battleController);

        controller.createBattle(new BattleDetails(null));

        verify(battleFactory).createBattle(anyObject());


    }

    @Test(expected = BattleIsActiveYet.class)
    public void testRemoveActiveBattleThrowsException() throws Exception {
        String battleId = "1";
        BattleController battleController = mock(BattleController.class);
        when(battleController.getBattleId()).thenReturn(battleId);
        when(battleController.isActive()).thenReturn(true);
        when(battleFactory.createBattle(anyObject())).thenReturn(battleController);
        controller.createBattle(new BattleDetails(null));

        controller.removeBattle(battleId);

    }

    @Test
    public void testRemoveBattle() throws Exception {
        String battleId = "1";
        BattleController battleController = mock(BattleController.class);
        when(battleController.getBattleId()).thenReturn(battleId);
        when(battleController.isActive()).thenReturn(false);
        when(battleFactory.createBattle(anyObject())).thenReturn(battleController);
        controller.createBattle(new BattleDetails(null));

        controller.removeBattle(battleId);

        verify(battleController).isActive();
        assertFalse(controller.battleExists(battleId));


    }

    @Test
    public void testBattleExistsReturnsFalseIfBattleNotExists() throws Exception {
        assertFalse(controller.battleExists("111"));
    }

    @Test
    public void testBattleExists() throws Exception {
        String battleId = "1";
        BattleController battleController = mock(BattleController.class);
        when(battleController.getBattleId()).thenReturn(battleId);
        when(battleFactory.createBattle(anyObject())).thenReturn(battleController);
        controller.createBattle(new BattleDetails(null));

        assertTrue(controller.battleExists(battleId));
    }

    @Test
    public void testGetNonExistingBattleController() throws Exception {
        Optional<BattleController> battleController = controller.getBattleController("test");
        assertFalse(battleController.isPresent());
    }

    @Test
    public void testGetBattleController() throws Exception {
        String battleId = "1";
        BattleController battleController = mock(BattleController.class);
        when(battleController.getBattleId()).thenReturn(battleId);
        when(battleFactory.createBattle(anyObject())).thenReturn(battleController);
        controller.createBattle(new BattleDetails(null));

        Optional<BattleController> optional = controller.getBattleController("1");

        assertTrue(optional.isPresent());
    }

    @Test
    public void testStartBattle() throws Exception {
        String battleId = "1";
        BattleController battleController = mock(BattleController.class);
        when(battleController.getBattleId()).thenReturn(battleId);
        when(battleFactory.createBattle(anyObject())).thenReturn(battleController);
        doNothing().when(taskExecutor).execute(anyObject());
        controller.createBattle(new BattleDetails(null));

        controller.startBattle(battleController);

        verify(taskExecutor).execute(anyObject());
    }

    @Test
    public void testEndBattle() throws Exception {

    }
}