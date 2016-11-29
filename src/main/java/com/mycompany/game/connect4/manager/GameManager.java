package com.mycompany.game.connect4.manager;

public interface GameManager {

  Game createNewGame(String userId, String color);

  Game getGame(String gameId);

  Game joinGame(String gameId, String userId);

  Game play(String gameId, String userId, int column);

}
