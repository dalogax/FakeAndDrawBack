package com.fakeanddraw.entrypoints.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fakeanddraw.domain.model.Game;
import com.fakeanddraw.domain.service.GameService;
import com.fakeanddraw.entrypoints.websocket.message.JoinMessage;

@Controller
public class GameController {

	@Autowired
	GameService gameService;

	@MessageMapping("/create")
	public void create(SimpMessageHeaderAccessor headerAccessor, @Header("simpSessionId") String sessionId)
			throws Exception {

		// Create new game
		Game game = gameService.createGame(headerAccessor.getSessionId());

		// Notify master with room code
		template.convertAndSendToUser(headerAccessor.getSessionId(), "/topic/roomCreated",
				"{\"roomCode\":\"" + game.getRoomCode() + "\"}");
	}

	@MessageMapping("/join")
	public void join(JoinMessage message, SimpMessageHeaderAccessor headerAccessor,
			@Header("simpSessionId") String sessionId) throws Exception {

		Game game = gameService.addPlayer(message.getRoomCode(), headerAccessor.getSessionId(), message.getName());
		// Notify master about new user joined
		template.convertAndSendToUser(game.getSessionId(), "/topic/playerJoined",
				"{\"user\":\"" + message.getName() + "\"}");

	}

	@Autowired
	private SimpMessagingTemplate template;
}
