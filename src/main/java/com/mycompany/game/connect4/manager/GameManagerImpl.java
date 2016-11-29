package com.mycompany.game.connect4.manager;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.game.connect4.common.DiscColor;
import com.mycompany.game.connect4.common.GameStatus;
import com.mycompany.game.connect4.dao.GameRepository;
import com.mycompany.game.connect4.exception.GameNotFoundException;
import com.mycompany.game.connect4.exception.InvalidGamePlayException;
import com.mycompany.game.connect4.exception.InvalidGameStatusException;
import com.mycompany.game.connect4.exception.NoPlayerSpaceException;

@Component
public class GameManagerImpl implements GameManager {

  @Autowired
  private GameRepository gameRepository;

  @Override
  public Game createNewGame(String userId, String color) {
    Player player = new Player();
    player.setUserId(userId);
    player.setDiscColor(DiscColor.valueOf(color));
    Game game = new Game();
    game.setId(UUID.randomUUID().toString());
    game.setPlayer1(player);
    gameRepository.save(game);
    return game;
  }

  @Override
  public Game getGame(String gameId) {
    Game game = gameRepository.getGame(gameId);
    return game;
  }

  @Override
  public Game joinGame(String gameId, String userId) {
    Game game = null;
    try {
      game = gameRepository.lockAndGet(gameId);
      if (game.getPlayer2() != null) {
        throw new NoPlayerSpaceException("Game does not have room for new player");
      }
      if (game.getPlayer1().getUserId().equals(userId)) {
        throw new NoPlayerSpaceException("Player already exists for the game");
      }
      Player player = new Player();
      player.setUserId(userId);
      DiscColor disColorForPlayer2 =
          game.getPlayer1().equals(DiscColor.RED) ? DiscColor.YELLOW : DiscColor.RED;
      player.setDiscColor(disColorForPlayer2);
      game.setPlayer2(player);
      game.setStatus(GameStatus.STARTED);
      gameRepository.save(game);
    } finally {
      gameRepository.unlock(game);
    }
    return game;
  }

  @Override
  public Game play(String gameId, String userId, int column) {
    Game game = null;
    try {
      game = gameRepository.lockAndGet(gameId);
      if (GameStatus.CREATED.equals(game.getStatus())) {
        throw new InvalidGameStatusException("Game does not have enough players");
      }
      if (!(game.getPlayer1().getUserId().equals(userId)
          || game.getPlayer2().getUserId().equals(userId))) {
        throw new GameNotFoundException("No game found for given userId");
      }
      if (GameStatus.COMPLETED.equals(game.getStatus())) {
        throw new InvalidGameStatusException("Game is already completed");
      }
      Player player =
          game.getPlayer1().getUserId().equals(userId) ? game.getPlayer1() : game.getPlayer2();
      if (player.getDiscColor().equals(game.getLastPlayedDisc())) {
        throw new InvalidGamePlayException("Game should be played by other player");
      }
      game.putDisc(player.getDiscColor(), column);
      if (game.calculateWinner()) {
        game.setStatus(GameStatus.COMPLETED);
      }
      gameRepository.save(game);
    } finally {
      gameRepository.unlock(game);
    }
    return game;
  }
}
