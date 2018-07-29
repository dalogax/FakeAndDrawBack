package com.fakeanddraw.domain.usecase;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fakeanddraw.dataproviders.repository.MatchRepository;
import com.fakeanddraw.dataproviders.repository.PlayerRepository;
import com.fakeanddraw.domain.model.Match;
import com.fakeanddraw.domain.model.Player;
import com.fakeanddraw.entrypoints.websocket.ResponseController;
import com.fakeanddraw.entrypoints.websocket.message.ErrorPayload;
import com.fakeanddraw.entrypoints.websocket.message.Message;
import com.fakeanddraw.entrypoints.websocket.message.MessageType;
import com.fakeanddraw.entrypoints.websocket.message.response.PlayerInfoPayload;

@Component
public class AddPlayerToGame implements UseCase<AddPlayerToGameRequest> {

  @Autowired
  private ResponseController responseController;

  @Autowired
  private MatchRepository matchRepository;

  @Autowired
  private PlayerRepository playerRepository;

  @Override
  public void execute(AddPlayerToGameRequest request) {
    Optional<Match> match = matchRepository.findLastMatchByGameCode(request.getGameCode());
    if (match.isPresent()) {

      Player newPlayer =
          playerRepository.create(new Player(request.getPlayerSessionId(), request.getUserName()));
      matchRepository.addPlayerToMatch(match.get(), newPlayer);

      Message userAddedMessage = new Message(MessageType.USER_ADDED.getType(),
          new PlayerInfoPayload(newPlayer.getPlayerId(), newPlayer.getUserName()));

      // Notify master about new user joined
      responseController.send(match.get().getGame().getSessionId(), userAddedMessage);

      // Notify to player that he has been added succesfully
      responseController.send(newPlayer.getSessionId(), userAddedMessage);
    } else {
      // Should notify user that the game code is not valid
      responseController.send(request.getPlayerSessionId(), new Message(
          MessageType.USER_ADDED.getType(), new ErrorPayload(1, "Game code not valid")));
    }
  }
}
