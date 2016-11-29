package com.mycompany.game.connect4.exception;

public class NoPlayerSpaceException extends GameException {
  
  private static final long serialVersionUID = 4412474112777551349L;

  public NoPlayerSpaceException(String message){
    super(message);
  }
}
