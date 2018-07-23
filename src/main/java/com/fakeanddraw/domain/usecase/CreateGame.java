package com.fakeanddraw.domain.usecase;

import java.sql.Timestamp;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.fakeanddraw.domain.model.Game;
import com.fakeanddraw.domain.model.Match;
import com.fakeanddraw.domain.repository.GameRepository;
import com.fakeanddraw.domain.repository.MatchRepository;
import com.fakeanddraw.entrypoints.websocket.ResponseController;
import com.fakeanddraw.entrypoints.websocket.message.Message;
import com.fakeanddraw.entrypoints.websocket.message.MessageType;
import com.fakeanddraw.entrypoints.websocket.message.response.GameCreatedMessagePayload;

@Component
public class CreateGame implements UseCase<String> {

  @Autowired
  private ResponseController responseController;

  @Autowired
  private GameRepository gameRepository;

  @Autowired
  private MatchRepository matchRepository;

  @Value("${game.timeout.join}")
  Integer joinTimeout;

  @Override
  public void execute(String sessionId) {
    Game newGame = gameRepository.create(new Game(sessionId));

    Match newMatch = matchRepository.create(new Match(newGame));

    Message gameCreatedMessage = new Message(MessageType.GAME_CREATED.getType(),
        new GameCreatedMessagePayload(newGame.getGameCode(),
            new Timestamp(new DateTime().plusSeconds(joinTimeout).getMillis())));

    // Notify master with room code
    responseController.send(sessionId, gameCreatedMessage);
  }
}
