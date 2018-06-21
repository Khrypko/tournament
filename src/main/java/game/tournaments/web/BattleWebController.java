package game.tournaments.web;

import game.tournaments.battle.BattleController;
import game.tournaments.battle.config.BattleDetails;
import game.tournaments.battle.config.battlefield.ecxeption.InsufficientDataToCreateBattlefield;
import game.tournaments.battle.exception.BattleCreationException;
import game.tournaments.core.MainController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by maks on 30.09.17.
 */
@RestController
public class BattleWebController {

    private MainController mainController;

    @Autowired
    public BattleWebController(MainController mainController) {
        this.mainController = mainController;
    }

    @RequestMapping(value = "/technical/battle", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<BattleResponse> createBattle(@RequestBody BattleRequest request){
        try {
            BattleController battleController = mainController.createBattle(BattleDetails.createFromRequest(request));

            BattleResponse response = new BattleResponse();
            response.setBattleId(battleController.getBattleId());
            response.setPlayersBattleTokens(battleController.getPlayerTokens());
            return new ResponseEntity<BattleResponse>(response, HttpStatus.OK);
        } catch (BattleCreationException | InsufficientDataToCreateBattlefield e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
