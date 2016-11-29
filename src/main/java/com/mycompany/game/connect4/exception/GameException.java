package com.mycompany.game.connect4.exception;

public class GameException extends RuntimeException {

  private static final long serialVersionUID = 3582014151639848256L;

  public GameException(Throwable e) {
    super(e);
  }
  
  public GameException(){
    
  }
  
  public GameException(String message){
    super(message);
  }

}
