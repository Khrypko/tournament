package game.tournaments.battle.config.battlefield;

import game.tournaments.battle.config.BattleDetails;
import game.tournaments.battle.config.battlefield.ecxeption.InsufficientDataToCreateBattlefield;
import game.tournaments.battle.core.battlefield.Battlefield;
import game.tournaments.battle.exception.BattleCreationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BattlefieldFactoryImplTest {

    private BattlefieldFactory battlefieldFactory;
    private BattleDetails details;

    @Before
    public void setUp() throws Exception {
        battlefieldFactory = new BattlefieldFactoryImpl();
        details = new BattleDetails(Arrays.asList(1l, 2l));
        details.setParameters(Collections.singletonMap("type", "TEST"));
    }

    @Test
    public void testNullPointerNotThrownWhileCreatingBattlefield() throws InsufficientDataToCreateBattlefield, BattleCreationException {
        battlefieldFactory.createBattlefield(details);
    }

    @Test(expected = InsufficientDataToCreateBattlefield.class)
    public void testNullExceptionThrownWithNullDetails() throws InsufficientDataToCreateBattlefield, BattleCreationException {
        battlefieldFactory.createBattlefield(null);
    }

    @Test
    public void testNullNotReturned() throws Exception {
        Battlefield battlefield = battlefieldFactory.createBattlefield(details);
        assertNotNull(battlefield);
    }
}