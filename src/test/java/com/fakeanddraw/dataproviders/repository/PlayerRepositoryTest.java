package com.fakeanddraw.dataproviders.repository;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.fakeanddraw.domain.model.Game;
import com.fakeanddraw.domain.model.GameFactory;
import com.fakeanddraw.domain.model.Match;
import com.fakeanddraw.domain.model.MatchFactory;
import com.fakeanddraw.domain.model.Player;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PlayerRepositoryTest {
  @Autowired
  private GameRepository gameRepository;

  @Autowired
  private MatchRepository matchRepository;

  @Autowired
  private PlayerRepository playerRepository;


  @Autowired
  private GameFactory gameFactory;

  @Autowired
  private MatchFactory matchFactory;

  @Test
  public void createAndAddToGame() {
    Game game = gameRepository.create(gameFactory.createNewGame("123asd"));

    Match match = matchFactory.createNewMatch(game);
    match.setJoinTimeout(match.getCreatedDate());
    match.setDrawTimeout(match.getCreatedDate());


    match = matchRepository.create(match);

    Player player = playerRepository.create(new Player("123asd", "Mike"));

    assertNotNull(player);
    assertNotNull(player.getPlayerId());

    matchRepository.addPlayerToMatch(match, player);

    List<Player> players = playerRepository.findPlayersByMatch(match.getMatchId());

    assertNotNull(players);
    assertEquals(1, players.size());
    assertEquals(players.get(0).getPlayerId(), player.getPlayerId());
    assertEquals(players.get(0).getSessionId(), player.getSessionId());
    assertEquals(players.get(0).getUserName(), player.getUserName());
  }
}
