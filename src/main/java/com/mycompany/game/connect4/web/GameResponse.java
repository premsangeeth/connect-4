package com.mycompany.game.connect4.web;

import com.mycompany.game.connect4.manager.Game;

public class GameResponse {
  
  private Game game;
  
  private String message;
  
  public GameResponse(Game game) {
    this.game=game;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
  
}
