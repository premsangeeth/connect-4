package com.mycompany.game.connect4.manager;

import org.junit.Assert;
import org.junit.Test;

import com.mycompany.game.connect4.common.DiscColor;
import com.mycompany.game.connect4.exception.InvalidGamePlayException;

public class GameTest {
  @Test
  public void testPutDiscShouldAddtoExactLocation() throws Exception {
    Game game = new Game();
    game.putDisc(DiscColor.RED, 0);
    Assert.assertEquals(game.getBoard()[5][0], DiscColor.RED);
  }
  
  @Test
  public void testPutDiscShouldAddtoExactLocationForMultipleDiscs() throws Exception {
    Game game = new Game();
    game.putDisc(DiscColor.RED, 0);
    game.putDisc(DiscColor.RED, 0);
    Assert.assertEquals(game.getBoard()[5][0], DiscColor.RED);
    Assert.assertEquals(game.getBoard()[4][0], DiscColor.RED);
  }
  
  @Test(expected=InvalidGamePlayException.class)
  public void testPutDiscShouldThrowExceptionIfRowIsFull() throws Exception {
    Game game = new Game();
    game.putDisc(DiscColor.RED, 0);
    game.putDisc(DiscColor.RED, 0);
    game.putDisc(DiscColor.RED, 0);
    game.putDisc(DiscColor.RED, 0);
    game.putDisc(DiscColor.RED, 0);
    game.putDisc(DiscColor.RED, 0);
    game.putDisc(DiscColor.RED, 0);
  }
  
  @Test
  public void testCalculateWinnerInRowShouldReturnTrue() throws Exception {
    Game game = new Game();
    game.putDisc(DiscColor.RED, 0);
    game.putDisc(DiscColor.RED, 1);
    game.putDisc(DiscColor.RED, 3);
    game.putDisc(DiscColor.RED, 2);
    Assert.assertTrue(game.calculateWinner());
  }
  
  @Test
  public void testCalculateWinnerInRowShouldReturnFalse() throws Exception {
    Game game = new Game();
    game.putDisc(DiscColor.RED, 0);
    game.putDisc(DiscColor.RED, 1);
    game.putDisc(DiscColor.RED, 4);
    game.putDisc(DiscColor.RED, 3);
    Assert.assertFalse(game.calculateWinner());
  }
  
  @Test
  public void testCalculateWinnerInRowShouldReturnFalseWithOtherDisc() throws Exception {
    Game game = new Game();
    game.putDisc(DiscColor.RED, 0);
    game.putDisc(DiscColor.RED, 1);
    game.putDisc(DiscColor.YELLOW, 2);
    game.putDisc(DiscColor.RED, 4);
    game.putDisc(DiscColor.RED, 3);
    Assert.assertFalse(game.calculateWinner());
  }
  
  
  @Test
  public void testCalculateWinnerInColumnShouldReturnTrue() throws Exception {
    Game game = new Game();
    game.putDisc(DiscColor.RED, 0);
    game.putDisc(DiscColor.RED, 0);
    game.putDisc(DiscColor.RED, 0);
    game.putDisc(DiscColor.RED, 0);
    Assert.assertTrue(game.calculateWinner());
  }
  
  @Test
  public void testCalculateWinnerInColumnShouldReturnFalse() throws Exception {
    Game game = new Game();
    game.putDisc(DiscColor.RED, 0);
    game.putDisc(DiscColor.RED, 0);
    game.putDisc(DiscColor.RED, 0);
    game.putDisc(DiscColor.YELLOW, 0);
    Assert.assertFalse(game.calculateWinner());
  }
  
  @Test
  public void testCalculateWinnerInBackwardDiagonalShouldReturnTrue() throws Exception {
    Game game = new Game();
    game.putDisc(DiscColor.RED, 3);
    game.putDisc(DiscColor.YELLOW, 3);
    game.putDisc(DiscColor.YELLOW, 3);
    game.putDisc(DiscColor.YELLOW, 3);
    
    game.putDisc(DiscColor.YELLOW, 2);
    game.putDisc(DiscColor.RED, 2);
    game.putDisc(DiscColor.YELLOW, 2);
    game.putDisc(DiscColor.YELLOW, 2);
    
    game.putDisc(DiscColor.YELLOW, 1);
    game.putDisc(DiscColor.YELLOW, 1);
    game.putDisc(DiscColor.RED, 1);
    game.putDisc(DiscColor.YELLOW, 1);
    
    game.putDisc(DiscColor.YELLOW, 0);
    game.putDisc(DiscColor.YELLOW, 0);
    game.putDisc(DiscColor.YELLOW, 0);
    game.putDisc(DiscColor.RED, 0);
    
    Assert.assertTrue(game.calculateWinner());
  }
  
  @Test
  public void testCalculateWinnerInForwardDiagonalShouldReturnTrue2() throws Exception {
    Game game = new Game();
    game.putDisc(DiscColor.RED, 3);
    game.putDisc(DiscColor.YELLOW, 3);
    game.putDisc(DiscColor.YELLOW, 3);
    game.putDisc(DiscColor.YELLOW, 3);
    
    game.putDisc(DiscColor.YELLOW, 2);
    game.putDisc(DiscColor.RED, 2);
    game.putDisc(DiscColor.YELLOW, 2);
    game.putDisc(DiscColor.YELLOW, 2);
    
    
    
    game.putDisc(DiscColor.YELLOW, 0);
    game.putDisc(DiscColor.YELLOW, 0);
    game.putDisc(DiscColor.YELLOW, 0);
    game.putDisc(DiscColor.RED, 0);
    
    game.putDisc(DiscColor.YELLOW, 1);
    game.putDisc(DiscColor.YELLOW, 1);
    game.putDisc(DiscColor.RED, 1);
    
    Assert.assertTrue(game.calculateWinner());
  }
  
  @Test
  public void testCalculateWinnerInForwardDiagonalShouldReturnFalse() throws Exception {
    Game game = new Game();
    game.putDisc(DiscColor.RED, 3);
    game.putDisc(DiscColor.YELLOW, 3);
    game.putDisc(DiscColor.YELLOW, 3);
    game.putDisc(DiscColor.YELLOW, 3);
    
    game.putDisc(DiscColor.YELLOW, 2);
    game.putDisc(DiscColor.RED, 2);
    game.putDisc(DiscColor.YELLOW, 2);
    game.putDisc(DiscColor.YELLOW, 2);
    
    
    
    game.putDisc(DiscColor.YELLOW, 0);
    game.putDisc(DiscColor.YELLOW, 0);
    game.putDisc(DiscColor.YELLOW, 0);
    game.putDisc(DiscColor.YELLOW, 0);
    
    game.putDisc(DiscColor.YELLOW, 1);
    game.putDisc(DiscColor.YELLOW, 1);
    game.putDisc(DiscColor.RED, 1);
    
    Assert.assertFalse(game.calculateWinner());
  }
  
  
  @Test
  public void testCalculateWinnerInBackWardDiagonalShouldReturnTrue() throws Exception {
    Game game = new Game();
    game.putDisc(DiscColor.RED, 0);
    game.putDisc(DiscColor.YELLOW, 0);
    game.putDisc(DiscColor.YELLOW, 0);
    game.putDisc(DiscColor.YELLOW, 0);
    
    game.putDisc(DiscColor.YELLOW, 1);
    game.putDisc(DiscColor.RED, 1);
    game.putDisc(DiscColor.YELLOW, 1);
    game.putDisc(DiscColor.YELLOW, 1);
    
    game.putDisc(DiscColor.YELLOW, 2);
    game.putDisc(DiscColor.YELLOW, 2);
    game.putDisc(DiscColor.RED, 2);
    game.putDisc(DiscColor.YELLOW, 2);
    
    game.putDisc(DiscColor.YELLOW, 3);
    game.putDisc(DiscColor.YELLOW, 3);
    game.putDisc(DiscColor.YELLOW, 3);
    game.putDisc(DiscColor.RED, 3);
    
    
    Assert.assertTrue(game.calculateWinner());
  }
  
  @Test
  public void testCalculateWinnerInBackwardDiagonalShouldReturnFalse() throws Exception {
    Game game = new Game();
    game.putDisc(DiscColor.YELLOW, 0);
    game.putDisc(DiscColor.YELLOW, 0);
    game.putDisc(DiscColor.YELLOW, 0);
    game.putDisc(DiscColor.YELLOW, 0);
    
    game.putDisc(DiscColor.YELLOW, 1);
    game.putDisc(DiscColor.RED, 1);
    game.putDisc(DiscColor.YELLOW, 1);
    game.putDisc(DiscColor.YELLOW, 1);
    
    game.putDisc(DiscColor.YELLOW, 2);
    game.putDisc(DiscColor.YELLOW, 2);
    game.putDisc(DiscColor.RED, 2);
    game.putDisc(DiscColor.YELLOW, 2);
    
    game.putDisc(DiscColor.YELLOW, 3);
    game.putDisc(DiscColor.YELLOW, 3);
    game.putDisc(DiscColor.YELLOW, 3);
    game.putDisc(DiscColor.RED, 3);
    
    Assert.assertFalse(game.calculateWinner());
  }
}
