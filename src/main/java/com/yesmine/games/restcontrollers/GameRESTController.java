package com.yesmine.games.restcontrollers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yesmine.games.entities.Game;
import com.yesmine.games.service.GameService;
@RestController
@RequestMapping("/api")
@CrossOrigin
public class GameRESTController {
    @Autowired
    GameService gameService;
    //@RequestMapping(method = RequestMethod.GET)
    @RequestMapping(path="all",method =RequestMethod.GET)
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }
    @RequestMapping(value="/getbyid/{id}",method = RequestMethod.GET)
    public Game getGameById(@PathVariable("id") Long id) {
        return gameService.getGame(id);
    }
    @RequestMapping(path="/addgame",method = RequestMethod.POST)
    public Game createGame(@RequestBody Game Game) {
        return gameService.saveGame(Game);
    }
    @RequestMapping(path="/updategame/{id}",method = RequestMethod.PUT)
    public Game updateGame(@RequestBody Game Game) {
        return gameService.updateGame(Game);
    }
    @RequestMapping(value="/delgame/{id}",method = RequestMethod.DELETE)
    public void deleteGame(@PathVariable("id") Long id)
    {
        gameService.deleteGameById(id);
    }
    @RequestMapping(value="/gamestype/{idType}",method = RequestMethod.GET)
    public List<Game> getGamesByCatId(@PathVariable("idType") Long idType) {
        return gameService.findByTypeIdType(idType);
    }

}