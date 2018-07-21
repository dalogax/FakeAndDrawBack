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
import com.fakeanddraw.entrypoints.websocket.message.request.CreateGameMessage;
import com.fakeanddraw.entrypoints.websocket.message.request.NewUserMessage;
import com.fakeanddraw.entrypoints.websocket.message.request.NewUserMessageBody;
import com.fakeanddraw.entrypoints.websocket.message.response.GameCreatedMessage;
import com.fakeanddraw.entrypoints.websocket.message.response.GameCreatedMessageBody;
import com.fakeanddraw.entrypoints.websocket.message.response.PlayerJoinedMessageBody;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class GameController {

	@Autowired
	CreateGame createGame;

	@Autowired
	AddPlayerToGame addPlayerToGame;

	@MessageMapping("/request")
	public void request(Message message, @Header("simpSessionId") String sessionId) throws Exception {
		
		
		if (message!=null && message.getType()!=null) {
			switch (message.getType()) {
				case CreateGameMessage.messageType:
					// Create new game
					Game newGame = createGame.execute(sessionId);
									
					Message gameCreatedMessage = new GameCreatedMessage(new GameCreatedMessageBody(newGame.getRoomCode(), null));
	
					// Notify master with room code
					template.convertAndSendToUser(sessionId, "/response",
							new ObjectMapper().writeValueAsString(gameCreatedMessage));
				
			
				case NewUserMessage.messageType:
					
					NewUserMessageBody newUserMessageBody = (NewUserMessageBody) message.getBody();
					
					
					Optional<Game> game = addPlayerToGame
					.execute(new AddPlayerToGameRequest(newUserMessageBody.getGameCode(), sessionId, newUserMessageBody.getNickName()));

//					if (game.isPresent()) {
//						// Notify master about new user joined
//						template.convertAndSendToUser(game.get().getSessionId(), "/playerJoined",
//								new ObjectMapper().writeValueAsString(new PlayerJoinedMessageBody(message.getName())));
//						
//						//Notificar al usuario que se ha unido correctamente
//					} else {
//						// Should notify user that the game code is not valid
//					}
			}
		}

		
	}


	@Autowired
	private SimpMessagingTemplate template;
}
