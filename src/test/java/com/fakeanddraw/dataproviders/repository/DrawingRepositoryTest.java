package com.fakeanddraw.dataproviders.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.fakeanddraw.domain.model.Drawing;
import com.fakeanddraw.domain.model.Game;
import com.fakeanddraw.domain.model.GameFactory;
import com.fakeanddraw.domain.model.Match;
import com.fakeanddraw.domain.model.MatchFactory;
import javassist.NotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DrawingRepositoryTest {

  @Autowired
  private GameRepository gameRepository;

  @Autowired
  private MatchRepository matchRepository;

  @Autowired
  private DrawingRepository drawingRepository;

  @Autowired
  private GameFactory gameFactory;

  @Autowired
  private MatchFactory matchFactory;

  @Test
  public void createUpdateAndFindById() throws NotFoundException {

    Game game = gameRepository.create(gameFactory.createNewGame("123asd"));

    Match match = matchRepository.create(matchFactory.createNewMatch(game));

    Optional<Match> matchOptional = matchRepository.findMatchById(match.getMatchId());

    Drawing drawing = new Drawing();
    drawing.setMatch(match);
    drawing = drawingRepository.create(drawing);

    assertNotNull(drawing.getDrawingId());

    Optional<Drawing> optionalDrawing = drawingRepository.findDrawingById(-1);
    assertFalse(optionalDrawing.isPresent());

    optionalDrawing = drawingRepository.findDrawingById(drawing.getDrawingId());
    assertTrue(optionalDrawing.isPresent());
    assertEquals(drawing.getDrawingId(), optionalDrawing.get().getDrawingId());
    assertEquals(drawing.getMatch().getMatchId(), optionalDrawing.get().getMatch().getMatchId());
    assertNull(optionalDrawing.get().getImage());

    drawing.setImage("image");
    drawingRepository.update(drawing);

    optionalDrawing = drawingRepository.findDrawingById(drawing.getDrawingId());
    assertEquals("image", optionalDrawing.get().getImage());
  }

  @Test(expected = NotFoundException.class)
  public void updateNotFound() throws NotFoundException {
    Drawing drawing = new Drawing();
    drawing.setDrawingId(-1);
    drawingRepository.update(drawing);
  }
}
