package com.mycompany.game.connect4.manager;

import java.io.Serializable;

import com.mycompany.game.connect4.common.DiscColor;

public class Player implements Serializable{

  private static final long serialVersionUID = -1398891252336639719L;
  
  private String userId;
  
  private DiscColor discColor;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public DiscColor getDiscColor() {
    return discColor;
  }

  public void setDiscColor(DiscColor discColor) {
    this.discColor = discColor;
  }

}
