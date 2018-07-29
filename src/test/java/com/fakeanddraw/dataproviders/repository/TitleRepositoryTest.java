package com.fakeanddraw.dataproviders.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.fakeanddraw.domain.model.Drawing;
import com.fakeanddraw.domain.model.Game;
import com.fakeanddraw.domain.model.GameFactory;
import com.fakeanddraw.domain.model.MasterTitle;
import com.fakeanddraw.domain.model.Match;
import com.fakeanddraw.domain.model.MatchFactory;
import com.fakeanddraw.domain.model.Player;
import com.fakeanddraw.domain.model.PlayerDrawing;
import com.fakeanddraw.domain.model.Title;

@RunWith(SpringRunner.class)
@SpringBootTest
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
  public void create() {

    Game game = gameRepository.create(gameFactory.createNewGame("123asd"));

    Match match = matchRepository.create(matchFactory.createNewMatch(game));

    Optional<Match> matchOptional = matchRepository.findMatchById(match.getMatchId());

    Drawing drawing = new Drawing();
    drawing.setMatch(match);
    drawing = drawingRepository.create(drawing);
    
    Player player = playerRepository.create(new Player("123asd", "Mike"));
    
    matchRepository.addPlayerToMatch(match, player);

    PlayerDrawing playerDrawing = new PlayerDrawing();
    playerDrawing.setPlayer(player);
    playerDrawing.setDrawing(drawing);
    playerDrawing.setDescription("title");
    titleRepository.create(playerDrawing);
  }
}
