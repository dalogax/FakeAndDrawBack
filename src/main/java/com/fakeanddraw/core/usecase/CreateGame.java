package com.fakeanddraw.core.usecase;

import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fakeanddraw.core.domain.Game;
import com.fakeanddraw.core.domain.GameFactory;
import com.fakeanddraw.core.domain.Match;
import com.fakeanddraw.core.domain.MatchFactory;
import com.fakeanddraw.dataproviders.repository.GameRepository;
import com.fakeanddraw.dataproviders.repository.MatchRepository;
import com.fakeanddraw.entrypoints.scheduler.Scheduler;
import com.fakeanddraw.entrypoints.scheduler.TimeoutFactory;
import com.fakeanddraw.entrypoints.scheduler.TimeoutType;
import com.fakeanddraw.entrypoints.websocket.ResponseController;
import com.fakeanddraw.entrypoints.websocket.message.Message;
import com.fakeanddraw.entrypoints.websocket.message.MessageType;
import com.fakeanddraw.entrypoints.websocket.message.response.GameCreatedPayload;

@Component
public class CreateGame implements UseCase<String> {

  @Autowired
  private ResponseController responseController;

  @Autowired
  private GameRepository gameRepository;

  @Autowired
  private MatchRepository matchRepository;

  @Autowired
  private Scheduler taskScheduler;

  @Autowired
  private GameFactory gameFactory;

  @Autowired
  private MatchFactory matchFactory;

  @Autowired
  private TimeoutFactory timeoutFactory;

  @Override
  public void execute(String sessionId) {
    // Create new game
    Game newGame = gameRepository.create(gameFactory.createNewGame(sessionId));
    // Create new match
    Match newMatch = matchRepository.create(matchFactory.createNewMatch(newGame));


    // Notify master client with game code and timeout
    Message gameCreatedMessage =
        new Message(MessageType.GAME_CREATED.getType(), new GameCreatedPayload(
            newGame.getGameCode(), new Timestamp(newMatch.getJoinTimeout().getMillis())));
    responseController.send(sessionId, gameCreatedMessage);

    // Schedule join timeout
    taskScheduler.schedule(timeoutFactory.createTimeout(TimeoutType.JOIN, newMatch.getMatchId()),
        newMatch.getJoinTimeout().toDate());
  }
}
