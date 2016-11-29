package com.mycompany.game.connect4.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class GameNotFoundException extends GameException {

  private static final long serialVersionUID = -7977715620903288570L;
  
  public GameNotFoundException(String message){
    super(message);
  }

}
