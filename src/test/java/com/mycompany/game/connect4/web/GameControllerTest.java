package com.mycompany.game.connect4.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import com.mycompany.game.connect4.common.DiscColor;
import com.mycompany.game.connect4.common.GameStatus;
import com.mycompany.game.connect4.dao.GameRepository;
import com.mycompany.game.connect4.exception.GameNotFoundException;
import com.mycompany.game.connect4.manager.Game;
import com.mycompany.game.connect4.manager.GameManager;
import com.mycompany.game.connect4.manager.GameManagerImpl;
import com.mycompany.game.connect4.manager.Player;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
@ActiveProfiles("test")
@ContextConfiguration(classes=TestConfiguration.class)
public class GameControllerTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private GameRepository gameRepository;

  @Test
  public void testCreateNewGameShouldThrowErrorForNullUserId() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/connect-4/new"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void testCreateNewGameShouldThrowErrorForNullColor() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/connect-4/new").param("userId", "abc"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test(expected = NestedServletException.class)
  public void testCreateNewGameShouldThrowErrorForInvalidColor() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/connect-4/new").param("userId", "abc").param("color",
        "BLACK"));
  }

  @Test
  public void testCreateNewGameShouldCreateGame() throws Exception {

    mvc.perform(MockMvcRequestBuilders.get("/connect-4/new").param("userId", "abc").param("color",
        "YELLOW")).andExpect(MockMvcResultMatchers.jsonPath("game.player1.userId").value("abc"));
    Mockito.verify(gameRepository).save(Mockito.any());
  }

  
  @Test
  public void testGetGameThrowErrorForInvalidGameId() throws Exception {
    Mockito.when(gameRepository.getGame(Mockito.eq("game3"))).thenThrow(new GameNotFoundException("message"));
    mvc.perform(MockMvcRequestBuilders.get("/connect-4/game3"))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
    
  }
  
  @Test
  public void testGetGameShouldProvideValid() throws Exception {
    Game game = new Game();
    game.setId("myId");
    Mockito.when(gameRepository.getGame(Mockito.eq("game3"))).thenReturn(game);
    mvc.perform(MockMvcRequestBuilders.get("/connect-4/game3"))
        .andExpect(MockMvcResultMatchers.jsonPath("game.id").value("myId"));
    
  }
  
  @Test
  public void testJoinGameShouldAddNewPlayer() throws Exception {
    Game game = new Game();
    game.setId("myId");
    Player player1 = new Player();
    player1.setDiscColor(DiscColor.RED);
    player1.setUserId("1");
    game.setPlayer1(player1);
    Mockito.when(gameRepository.lockAndGet(Mockito.eq("game3"))).thenReturn(game);
    mvc.perform(MockMvcRequestBuilders.put("/connect-4/game3/join/user2"))
        .andExpect(MockMvcResultMatchers.jsonPath("game.player2.userId").value("user2"));
  }
  
  @Test(expected=NestedServletException.class)
  public void testJoinGameShouldNotAddNewPlayerIfNoPlayerSpace() throws Exception {
    Game game = new Game();
    game.setId("myId");
    Player player1 = new Player();
    player1.setDiscColor(DiscColor.RED);
    player1.setUserId("1");
    game.setPlayer1(player1);
    game.setPlayer2(player1);
    Mockito.when(gameRepository.lockAndGet(Mockito.eq("game3"))).thenReturn(game);
    mvc.perform(MockMvcRequestBuilders.put("/connect-4/game3/join/user2"));
  }
  
  @Test(expected=NestedServletException.class)
  public void testJoinGameShouldNotAddNewPlayerIfPlayerAlreadyExist() throws Exception {
    Game game = new Game();
    game.setId("myId");
    Player player1 = new Player();
    player1.setDiscColor(DiscColor.RED);
    player1.setUserId("1");
    game.setPlayer1(player1);
    Mockito.when(gameRepository.lockAndGet(Mockito.eq("game3"))).thenReturn(game);
    mvc.perform(MockMvcRequestBuilders.put("/connect-4/game3/join/1"));
  }
  
  @Test
  public void testPlayGameShouldAddDisc() throws Exception {
    Game game = new Game();
    game.setId("myId");
    Player player1 = new Player();
    player1.setDiscColor(DiscColor.RED);
    player1.setUserId("1");
    game.setPlayer1(player1);
    Player player2 = new Player();
    player2.setDiscColor(DiscColor.YELLOW);
    game.setPlayer2(player2);
    game.setStatus(GameStatus.STARTED);
    Mockito.when(gameRepository.lockAndGet(Mockito.eq("game3"))).thenReturn(game);
    mvc.perform(MockMvcRequestBuilders.put("/connect-4/game3/play/1/0"))
        .andExpect(MockMvcResultMatchers.jsonPath("game.board[5][0]").value("RED"));
  }
  
  @Test(expected=NestedServletException.class)
  public void testPlayGameShouldThrowExceptionIfNOEnoughplayers() throws Exception {
    Game game = new Game();
    game.setId("myId");
    Player player1 = new Player();
    player1.setDiscColor(DiscColor.RED);
    player1.setUserId("1");
    game.setPlayer1(player1);
    Mockito.when(gameRepository.lockAndGet(Mockito.eq("game3"))).thenReturn(game);
    mvc.perform(MockMvcRequestBuilders.put("/connect-4/game3/play/1/0"));
  }
}

@Configuration
@Profile("test")
class TestConfiguration {
  
  @Bean
  public GameController gameController() {
    return new GameController();
  }
  
  @Bean
  public GameManager gameManager() {
    return new GameManagerImpl();
  }
  
  @Bean
  public GameRepository gameRepository() {
    return Mockito.mock(GameRepository.class);
  }
}
