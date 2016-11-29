package com.mycompany.game.connect4.dao;

import java.util.concurrent.locks.Lock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.support.locks.LockRegistry;

import com.mycompany.game.connect4.exception.GameNotFoundException;
import com.mycompany.game.connect4.manager.Game;


@RunWith(MockitoJUnitRunner.class)
public class RedisGameRepositoryTest {

  @InjectMocks
  private RedisGameRepository gameRepository;
  
  @Mock
  private RedisTemplate<String, Game> redisTemplate;
  
  @Mock
  private HashOperations<String,Object,Object> hashOps;
  
  @Mock
  private LockRegistry redisLockRegistry;
  
  @Mock
  private Lock lock;
  
  @Before
  public void setup(){
    Mockito.when(redisTemplate.opsForHash()).thenReturn(hashOps);
    Mockito.when(redisLockRegistry.obtain(Mockito.any())).thenReturn(lock);
    gameRepository.setRedisLockRegistry(redisLockRegistry);
  }
  
  @Test(expected=GameNotFoundException.class)
  public void testGetGameShouldThrowExceptionIfGameIsNotPresent() throws Exception {
   Mockito.when(hashOps.get(Mockito.any(), Mockito.any())).thenReturn(null);
    gameRepository.getGame("id");
  }
  
  @Test
  public void testGetGameShouldReturnValueFromCache() throws Exception {
    Game game = new Game();
    game.setId("abc");
    Mockito.when(hashOps.get(Mockito.any(), Mockito.any())).thenReturn(game);
     Game result = gameRepository.getGame("abc");
     Assert.assertEquals(game, result);
   }
  
  @Test
  public void testSaveShouldInvokeRedisSave() throws Exception {
     Game game = new Game();
     game.setId("abc");
    gameRepository.save(game);
     Mockito.verify(hashOps,Mockito.times(1)).put(Mockito.eq("abc"), Mockito.eq(Game.OBJECT_KEY), Mockito.eq(game));
   }
  
  @Test
  public void testLockAndGetGameShouldLockAndReturnValueFromCache() throws Exception {
    Game game = new Game();
    game.setId("id");
    Mockito.when(hashOps.get(Mockito.any(), Mockito.any())).thenReturn(game);
     Game result = gameRepository.lockAndGet("id");
     Assert.assertEquals(game, result);
     Assert.assertEquals(lock, game.getLock());
     Mockito.verify(redisLockRegistry,Mockito.times(1)).obtain(Mockito.eq("id"));
     Mockito.verify(lock).lock();
   }
  
  @Test
  public void testUnlockShouldUnlockFromRedisLock() throws Exception {
    Game game = new Game();
    game.setId("abc");
    game.setLock(lock);
     gameRepository.unlock(game);
     Mockito.verify(lock).unlock();
   }
}
