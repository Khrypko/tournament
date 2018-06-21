package game.tournaments.battle.core.command;

import game.tournaments.battle.core.Player;
import game.tournaments.battle.core.command.exception.IncorrectDataForCommand;

/**
 * Created by maks on 08.10.17.
 *
 * Command for:
 *  - surrender
 *
 */
public class SpecialCommand extends Command {

    private Player player;
    private CommandAction action;

    public SpecialCommand(Player player, int action) throws IncorrectDataForCommand {
        this.player = player;

        try {
            this.action = CommandAction.values()[action];
        } catch (IndexOutOfBoundsException e){
            throw new IncorrectDataForCommand("No action with code " + action);
        }
    }

    @Override
    public void execute() {
        switch (action){
            case SURRENDER:
                surrender();
                break;
        }
    }

    private void surrender() {
        player.surrender();
    }

    private enum CommandAction {
        SURRENDER
    }
}
