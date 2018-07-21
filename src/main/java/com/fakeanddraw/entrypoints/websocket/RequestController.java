package com.fakeanddraw.entrypoints.websocket;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fakeanddraw.domain.model.Game;
import com.fakeanddraw.domain.usecase.AddPlayerToGame;
import com.fakeanddraw.domain.usecase.AddPlayerToGameRequest;
import com.fakeanddraw.domain.usecase.CreateGame;
import com.fakeanddraw.entrypoints.websocket.message.request.NewUserMessagePayload;
import com.fakeanddraw.entrypoints.websocket.message.response.GameCreatedMessagePayload;
import com.fakeanddraw.entrypoints.websocket.message.response.UserAddedMessagePayload;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class RequestController {

	@Autowired
	CreateGame createGame;

	@Autowired
	AddPlayerToGame addPlayerToGame;

	@MessageMapping("/request")
	public void request(Message message, @Header("simpSessionId") String sessionId) throws Exception {

		if (message != null && message.getType() != null) {
			switch (MessageType.findByType(message.getType())) {
			case CREATE_GAME:
				// Create new game
				Game newGame = createGame.execute(sessionId);

				Message gameCreatedMessage = new Message(MessageType.GAME_CREATED.getType(),
						new GameCreatedMessagePayload(newGame.getRoomCode(), null));

				// Notify master with room code
				template.convertAndSendToUser(sessionId, "/response",
						new ObjectMapper().writeValueAsString(gameCreatedMessage));
				break;

			case NEW_USER:
				NewUserMessagePayload newUserMessagePayload = (NewUserMessagePayload) message.getMessagePayload();

				Optional<Game> game = addPlayerToGame.execute(new AddPlayerToGameRequest(
						newUserMessagePayload.getGameCode(), sessionId, newUserMessagePayload.getNickname()));

				if (game.isPresent()) {

					Message userAddedMessage = new Message(MessageType.USER_ADDED.getType(),
							new UserAddedMessagePayload(newUserMessagePayload.getNickname()));

					// Notify master about new user joined
					template.convertAndSendToUser(game.get().getSessionId(), "/response",
							new ObjectMapper().writeValueAsString(userAddedMessage));

					// Notificar al usuario que se ha unido correctamente
					template.convertAndSendToUser(sessionId, "/response",
							new ObjectMapper().writeValueAsString(userAddedMessage));
				} else {
					// Should notify user that the game code is not valid

					template.convertAndSendToUser(game.get().getSessionId(), "/response",
							new ObjectMapper().writeValueAsString(new ErrorMessagePayload(1, "Game code not valid")));
				}
				break;
			}
		}
	}

	@Autowired
	private SimpMessagingTemplate template;
}
