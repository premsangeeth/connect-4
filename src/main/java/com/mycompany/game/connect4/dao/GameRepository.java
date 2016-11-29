package com.mycompany.game.connect4.dao;

import com.mycompany.game.connect4.manager.Game;

public interface GameRepository {

  void save(Game game);

  Game getGame(String id);

  Game lockAndGet(String gameId);

  void unlock(Game game);

}
