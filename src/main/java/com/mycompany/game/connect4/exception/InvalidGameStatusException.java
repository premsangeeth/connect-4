package com.mycompany.game.connect4.exception;

public class InvalidGameStatusException extends GameException {

  private static final long serialVersionUID = 186086489938989134L;
  
  public InvalidGameStatusException(String message){
    super(message);
  }

}
