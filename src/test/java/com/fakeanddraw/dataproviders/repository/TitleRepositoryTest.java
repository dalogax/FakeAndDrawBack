package com.fakeanddraw.dataproviders.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.fakeanddraw.core.domain.Drawing;
import com.fakeanddraw.core.domain.Game;
import com.fakeanddraw.core.domain.GameFactory;
import com.fakeanddraw.core.domain.MasterTitle;
import com.fakeanddraw.core.domain.Match;
import com.fakeanddraw.core.domain.MatchFactory;
import com.fakeanddraw.core.domain.Player;
import com.fakeanddraw.core.domain.Title;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TitleRepositoryTest {

  @Autowired
  private TitleRepository titleRepository;

  @Autowired
  private GameRepository gameRepository;

  @Autowired
  private MatchRepository matchRepository;

  @Autowired
  private GameFactory gameFactory;

  @Autowired
  private MatchFactory matchFactory;

  @Autowired
  private DrawingRepository drawingRepository;

  @Autowired
  private PlayerRepository playerRepository;

  @Test
  public void getMasterTitles() {
    List<MasterTitle> masterTitles = titleRepository.getMasterTitles(2);
    assertNotNull(masterTitles);
    assertTrue(!masterTitles.isEmpty());
    assertEquals(2, masterTitles.size());
  }

  @Test
  public void createAndFindByPlayerSessionId() {

    Game game = gameRepository.create(gameFactory.createNewGame("123asd"));

    Match match = matchRepository.create(matchFactory.createNewMatch(game));

    Drawing drawing = drawingRepository.create(new Drawing(match));

    Player player = playerRepository.create(new Player("123rew", "Mike"));

    matchRepository.addPlayerToMatch(match, player);

    Player player2 = playerRepository.create(new Player("456rew", "Peter"));

    matchRepository.addPlayerToMatch(match, player2);

    Title title = new Title(drawing, player2, "title", true);
    titleRepository.create(title);

    Optional<Title> optionalTitle = titleRepository.findCurrentTitleByPlayerSessionId("aaaa");

    assertFalse(optionalTitle.isPresent());

    optionalTitle = titleRepository.findCurrentTitleByPlayerSessionId(player2.getSessionId());

    assertTrue(optionalTitle.isPresent());

    assertEquals(title.getDescription(), optionalTitle.get().getDescription());
    assertEquals(title.getIsOriginal(), optionalTitle.get().getIsOriginal());
    assertEquals(title.getPlayer().getPlayerId(), optionalTitle.get().getPlayer().getPlayerId());
    assertEquals(title.getPlayer().getSessionId(), optionalTitle.get().getPlayer().getSessionId());
    assertEquals(title.getPlayer().getUserName(), optionalTitle.get().getPlayer().getUserName());
    assertEquals(title.getDrawing().getDrawingId(),
        optionalTitle.get().getDrawing().getDrawingId());
    assertEquals(title.getDrawing().getMatch().getMatchId(),
        optionalTitle.get().getDrawing().getMatch().getMatchId());
    assertEquals(title.getDrawing().getMatch().getGame().getGameId(),
        optionalTitle.get().getDrawing().getMatch().getGame().getGameId());
  }
}
