package game.tournaments.battle.core;

import game.tournaments.battle.core.battlefield.Battlefield;
import game.tournaments.battle.core.command.Command;

import java.util.stream.Stream;

/**
 * Created by maks on 24.09.17.
 */
public class CommandProcessor {

    private Battlefield battlefield;
    private GameEngine gameEngine;

    public void process(Stream<Command> commands){
        commands.forEach(Command::execute);

    }

}
