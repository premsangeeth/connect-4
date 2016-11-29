package com.mycompany.game.connect4.web;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.game.connect4.common.DiscColor;
import com.mycompany.game.connect4.common.GameStatus;
import com.mycompany.game.connect4.manager.Game;
import com.mycompany.game.connect4.manager.GameManager;

@RestController
@RequestMapping("/connect-4")
public class GameController {

  @Autowired
  private GameManager gameManager;

  @RequestMapping("/new")
  public GameResponse startNewGame(@RequestParam @NotNull String userId,
      @RequestParam @NotNull String color) {
    validateColor(color);
    Game game = gameManager.createNewGame(userId, color);
    GameResponse gameResponse = new GameResponse(game);
    gameResponse.setMessage("Game is Created successfully");
    return gameResponse;
  }

  private void validateColor(String color) {
    try {
      DiscColor.valueOf(color);
    } catch (Exception e) {
      throw new IllegalArgumentException(
          "valid values of color are " + DiscColor.RED + " and " + DiscColor.YELLOW);
    }
  }

  @RequestMapping("/{gameId}")
  public GameResponse getGame(@PathVariable @NotNull String gameId) {
    Game game = gameManager.getGame(gameId);
    GameResponse gameResponse = new GameResponse(game);
    gameResponse.setMessage("Game is Retrieved");
    return gameResponse;
  }

  @RequestMapping(path = "/{gameId}/join/{userId}",method=RequestMethod.PUT)
  public GameResponse joinGame(@PathVariable @NotNull String gameId,
      @PathVariable @NotNull String userId) {
    Game game = gameManager.joinGame(gameId, userId);
    GameResponse gameResponse = new GameResponse(game);
    gameResponse.setMessage(userId + " joined the game " + gameId + " with disc color "
        + game.getPlayer2().getDiscColor());
    return gameResponse;
  }

  @RequestMapping(path="/{gameId}/play/{userId}/{column}",method=RequestMethod.PUT)
  public GameResponse playGame(@PathVariable @NotNull String gameId,
      @PathVariable @NotNull String userId, @PathVariable  int column) {
    if(column<0 || column>6){
      throw new IllegalArgumentException("Column value should be between 0 and 6");
    }
    Game game = gameManager.play(gameId, userId, column);
    GameResponse gameResponse = new GameResponse(game);
    gameResponse.setMessage(userId + " put disc on column " + column + " for the game " + gameId);
    if (GameStatus.COMPLETED.equals(game.getStatus())) {
      gameResponse.setMessage(gameResponse.getMessage() + " User " + userId + " won the game");
    }
    return gameResponse;
  }
}
