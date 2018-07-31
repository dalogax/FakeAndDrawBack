package com.fakeanddraw.dataproviders.repository;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.fakeanddraw.core.domain.Game;
import com.fakeanddraw.core.domain.GameFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GameRepositoryTest {

  @Autowired
  private GameRepository gameRepository;

  @Autowired
  private GameFactory gameFactory;

  @Test
  public void createAndFindById() {
    Optional<Game> gameOptional = gameRepository.findById(-1);
    assertFalse(gameOptional.isPresent());

    Game game = gameRepository.create(gameFactory.createNewGame("123asd"));
    gameOptional = gameRepository.findById(game.getGameId());

    assertTrue(gameOptional.isPresent());
    assertNotNull(gameOptional.get());
    assertEquals(game.getGameId(), gameOptional.get().getGameId());
    assertEquals(game.getSessionId(), gameOptional.get().getSessionId());
    assertEquals(game.getGameCode(), gameOptional.get().getGameCode());
  }
}
