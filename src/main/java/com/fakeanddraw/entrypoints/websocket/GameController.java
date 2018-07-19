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
import com.fakeanddraw.entrypoints.websocket.message.JoinMessage;

@Controller
@MessageMapping("/game/")
public class GameController {

	@Autowired
	CreateGame createGame;

	@Autowired
	AddPlayerToGame addPlayerToGame;

	@MessageMapping("/create")
	public void create(@Header("simpSessionId") String sessionId) throws Exception {

		// Create new game
		Game game = createGame.execute(sessionId);

		// Notify master with room code
		template.convertAndSendToUser(sessionId, "/roomCreated", "{\"roomCode\":\"" + game.getRoomCode() + "\"}");
	}

	@MessageMapping("/join")
	public void join(JoinMessage message, @Header("simpSessionId") String sessionId) throws Exception {

		Optional<Game> game = addPlayerToGame
				.execute(new AddPlayerToGameRequest(message.getRoomCode(), sessionId, message.getName()));

		if (game.isPresent()) {
			// Notify master about new user joined
			template.convertAndSendToUser(game.get().getSessionId(), "/playerJoined",
					"{\"user\":\"" + message.getName() + "\"}");
		} else {
			// Should notify user that the game code is not valid
		}
	}

	@Autowired
	private SimpMessagingTemplate template;
}
