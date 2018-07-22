package com.fakeanddraw.domain.usecase;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fakeanddraw.domain.model.Game;
import com.fakeanddraw.domain.model.Player;
import com.fakeanddraw.domain.repository.GameRepository;
import com.fakeanddraw.domain.repository.PlayerRepository;
import com.fakeanddraw.entrypoints.websocket.ResponseController;
import com.fakeanddraw.entrypoints.websocket.message.ErrorMessagePayload;
import com.fakeanddraw.entrypoints.websocket.message.Message;
import com.fakeanddraw.entrypoints.websocket.message.MessageType;
import com.fakeanddraw.entrypoints.websocket.message.response.UserAddedMessagePayload;

@Component
public class AddPlayerToGame implements UseCase<AddPlayerToGameRequest> {

  @Autowired
  private ResponseController responseController;

  @Autowired
  private GameRepository gameRepository;

  @Autowired
  private PlayerRepository playerRepository;

  @Override
  public void execute(AddPlayerToGameRequest request) {
    Optional<Game> game = gameRepository.findByCode(request.getRoomCode());
    if (game.isPresent()) {
      Player newPlayer =
          playerRepository.create(new Player(request.getPlayerSessionId(), request.getUserName()));
      gameRepository.addPlayerToGame(game.get(), newPlayer);

      Message userAddedMessage = new Message(MessageType.USER_ADDED.getType(),
          new UserAddedMessagePayload(newPlayer.getUserName()));

      // Notify master about new user joined
      responseController.send(game.get().getSessionId(), userAddedMessage);

      // Notify to player that he has been addes succesfully
      responseController.send(newPlayer.getSessionId(), userAddedMessage);
    } else {
      // Should notify user that the game code is not valid
      responseController.send(game.get().getSessionId(), new Message(
          MessageType.USER_ADDED.getType(), new ErrorMessagePayload(1, "Game code not valid")));
    }
  }
}
