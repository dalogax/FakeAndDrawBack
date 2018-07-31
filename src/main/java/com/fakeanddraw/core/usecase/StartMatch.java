package com.fakeanddraw.core.usecase;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fakeanddraw.core.domain.Drawing;
import com.fakeanddraw.core.domain.MasterTitle;
import com.fakeanddraw.core.domain.Match;
import com.fakeanddraw.core.domain.MatchStatus;
import com.fakeanddraw.core.domain.Player;
import com.fakeanddraw.core.domain.Title;
import com.fakeanddraw.dataproviders.repository.DrawingRepository;
import com.fakeanddraw.dataproviders.repository.MatchRepository;
import com.fakeanddraw.dataproviders.repository.PlayerRepository;
import com.fakeanddraw.dataproviders.repository.TitleRepository;
import com.fakeanddraw.entrypoints.scheduler.Scheduler;
import com.fakeanddraw.entrypoints.scheduler.TimeoutFactory;
import com.fakeanddraw.entrypoints.scheduler.TimeoutType;
import com.fakeanddraw.entrypoints.websocket.ResponseController;
import com.fakeanddraw.entrypoints.websocket.message.Message;
import com.fakeanddraw.entrypoints.websocket.message.MessageType;
import com.fakeanddraw.entrypoints.websocket.message.response.DrawingStartedPayload;
import com.fakeanddraw.entrypoints.websocket.message.response.TitleAssignPayload;
import javassist.NotFoundException;

@Component
public class StartMatch implements UseCase<Integer> {

  @Autowired
  private ResponseController responseController;

  @Autowired
  private MatchRepository matchRepository;

  @Autowired
  private PlayerRepository playerRepository;

  @Autowired
  private TitleRepository titleRepository;

  @Autowired
  private DrawingRepository drawingRepository;

  @Autowired
  private Scheduler taskScheduler;

  @Autowired
  private TimeoutFactory timeoutFactory;

  private final Logger logger = LoggerFactory.getLogger(StartMatch.class);

  @Override
  public void execute(Integer matchId) {
    logger.info("StartMatch triggered for match {}", matchId);

    // Get the match
    Optional<Match> optionalMatch = matchRepository.findMatchById(matchId);

    if (optionalMatch.isPresent()) {
      Match match = optionalMatch.get();

      // Get players
      List<Player> players = playerRepository.findPlayersByMatch(matchId);

      // Check that at least 2 players joined
      if (players.size() >= 2) {

        // Send drawing started message to master client
        Message drawingStartedMessage = new Message(MessageType.DRAWING_STARTED.getType(),
            new DrawingStartedPayload(new Timestamp(match.getDrawTimeout().getMillis())));
        responseController.send(match.getGame().getSessionId(), drawingStartedMessage);

        // Get a new title for each player
        List<MasterTitle> titles = titleRepository.getMasterTitles(players.size());
        for (int i = 0; i < players.size(); i++) {

          // Save the title with an empty drawing
          Drawing drawing = new Drawing(match);
          drawing = drawingRepository.create(drawing);
          Title title = new Title(drawing, players.get(i), titles.get(i).getDescription(), true);
          titleRepository.create(title);

          // Send title assign message to each player
          Message titleAssignMessage =
              new Message(MessageType.TITLE_ASSIGN.getType(), new TitleAssignPayload(
                  new Timestamp(match.getDrawTimeout().getMillis()), title.getDescription()));
          responseController.send(title.getPlayer().getSessionId(), titleAssignMessage);
        }

        // Schedule new draw timeout. It should trigger StartRound use
        // case.
        taskScheduler.schedule(timeoutFactory.createTimeout(TimeoutType.DRAW, match.getMatchId()),
            match.getDrawTimeout().toDate());

        // Update Match status to Draw
        match.setStatus(MatchStatus.DRAW);
        try {
          matchRepository.update(match);
        } catch (NotFoundException e) {
          logger.error(
              "Something very weird happened. We failed updating a match we just got. MatchId {}",
              match.getMatchId());
        }

      } else {
        logger.info("Not enough players to start the match {}. {} players found", matchId,
            players.size());
        /*
         * TODO Should extend join time and notify master
         */
      }
    } else {
      logger.info("Something went wrong, match {} not found", matchId);
    }
  }
}
