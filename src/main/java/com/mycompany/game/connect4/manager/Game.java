package com.mycompany.game.connect4.manager;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mycompany.game.connect4.common.DiscColor;
import com.mycompany.game.connect4.common.GameStatus;
import com.mycompany.game.connect4.exception.InvalidGamePlayException;

public class Game implements Serializable {

  private static final long serialVersionUID = 4931370024375423489L;

  public static final String OBJECT_KEY = "GAME";

  private String id;

  private Player player1;

  private Player player2;

  private DiscColor[][] board = new DiscColor[6][7];

  private int lastPlayedX = -1;

  private int lastPlayedY = -1;

  private GameStatus status = GameStatus.CREATED;

  @JsonIgnore
  private transient Lock lock;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Player getPlayer1() {
    return player1;
  }

  public void setPlayer1(Player player1) {
    this.player1 = player1;
  }

  public Player getPlayer2() {
    return player2;
  }

  public void setPlayer2(Player player2) {
    this.player2 = player2;
  }

  public int getLastPlayedX() {
    return lastPlayedX;
  }

  public void setLastPlayedX(int lastPlayedX) {
    this.lastPlayedX = lastPlayedX;
  }

  public int getLastPlayedY() {
    return lastPlayedY;
  }

  public void setLastPlayedY(int lastPlayedY) {
    this.lastPlayedY = lastPlayedY;
  }

  public Lock getLock() {
    return lock;
  }

  public void setLock(Lock lock) {
    this.lock = lock;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(id).toHashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof Game) && this.id.equals(((Game) obj).id);
  }

  public DiscColor[][] getBoard() {
    return board;
  }

  public void setBoard(DiscColor[][] board) {
    this.board = board;
  }

  public GameStatus getStatus() {
    return status;
  }

  public void setStatus(GameStatus status) {
    this.status = status;
  }

  @JsonIgnore
  public DiscColor getLastPlayedDisc() {
    if (lastPlayedX == -1 && lastPlayedY == -1) {
      return player2.getDiscColor();
    }
    return board[lastPlayedX][lastPlayedY];
  }

  public boolean calculateWinner() {
    return validateInRow() || validateInColumn() || validateInForwardDiagonal()
        || validateInReverseDiagonal();
  }

  private boolean validateInRow() {
    int start = (lastPlayedY - 3) > 0 ? lastPlayedY - 3 : 0;
    int end = (lastPlayedY + 3) < 6 ? lastPlayedY + 3 : 6;
    int count = 0;
    for (int i = start; i <= end; i++) {
      if (getLastPlayedDisc().equals(board[lastPlayedX][i])) {
        count++;
        if (count == 4) {
          return true;
        }
      } else {
        count = 0;
      }
    }
    return false;
  }

  private boolean validateInColumn() {
    int start = (lastPlayedX - 3) > 0 ? lastPlayedX - 3 : 0;
    int end = (lastPlayedX + 3) < 6 ? lastPlayedX + 3 : 5;
    int count = 0;
    for (int i = start; i <= end; i++) {
      if (getLastPlayedDisc().equals(board[i][lastPlayedY])) {
        count++;
        if (count == 4) {
          return true;
        }
      } else {
        count = 0;
      }
    }
    return false;
  }

  private boolean validateInForwardDiagonal() {
    int startX = lastPlayedX;
    int startY = lastPlayedY;
    for (int i = 0; i < 3; i++) {
      if (startX == 0 || startY == 0) {
        break;
      }
      startX--;
      startY--;
    }

    int endX = lastPlayedX;
    int endY = lastPlayedY;
    for (int i = 0; i < 3; i++) {
      if (endX == 5 || endY == 6) {
        break;
      }
      endX++;
      endY++;
    }

    int count = 0;
    for (int i = startX, j = startY; i <= endX || j <= endY; i++, j++) {
      if (getLastPlayedDisc().equals(board[i][j])) {
        count++;
        if (count == 4) {
          return true;
        }
      } else {
        count = 0;
      }
    }
    return false;
  }

  private boolean validateInReverseDiagonal() {
    int startX = lastPlayedX;
    int startY = lastPlayedY;
    for (int i = 0; i < 3; i++) {
      if (startX == 0 || startY == 6) {
        break;
      }
      startX--;
      startY++;
    }

    int endX = lastPlayedX;
    int endY = lastPlayedY;
    for (int i = 0; i < 3; i++) {
      if (endX == 5 || endY == 0) {
        break;
      }
      endX++;
      endY--;
    }

    int count = 0;
    for (int i = startX, j = startY; i <= endX || j >= endY; i++, j--) {
      if (getLastPlayedDisc().equals(board[i][j])) {
        count++;
        if (count == 4) {
          return true;
        }
      } else {
        count = 0;
      }
    }
    return false;
  }

  public void putDisc(DiscColor discColor, int column) {
      if (board[0][column] != null) {
        throw new InvalidGamePlayException("No space for given column");
      }
      for (int i = 5; i >= 0; i--) {
        if (board[i][column] == null) {
          board[i][column] = discColor;
          this.lastPlayedX=i;
          this.lastPlayedY=column;
          break;
        }
    }
  }

}
