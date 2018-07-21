package com.fakeanddraw.entrypoints.websocket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.fakeanddraw.domain.usecase.AddPlayerToGame;
import com.fakeanddraw.domain.usecase.AddPlayerToGameRequest;
import com.fakeanddraw.domain.usecase.CreateGame;
import com.fakeanddraw.entrypoints.websocket.message.Message;
import com.fakeanddraw.entrypoints.websocket.message.MessageType;
import com.fakeanddraw.entrypoints.websocket.message.request.NewUserMessagePayload;

@Controller
public class RequestController {
	
	@Autowired
	CreateGame createGame;

	@Autowired
	AddPlayerToGame addPlayerToGame;
	
	private final Log log = LogFactory.getLog(RequestController.class);

	@MessageMapping("/request")
	public void request(Message message, @Header("simpSessionId") String sessionId) throws Exception {

		if (message != null && message.getType() != null) {
			switch (MessageType.findByType(message.getType())) {
			case CREATE_GAME:
				createGame.execute(sessionId);
				break;

			case NEW_USER:
				NewUserMessagePayload newUserMessagePayload = (NewUserMessagePayload) message.getMessagePayload();

				addPlayerToGame.execute(new AddPlayerToGameRequest(
						newUserMessagePayload.getGameCode(), sessionId, newUserMessagePayload.getNickname()));
				
				break;
			}
		}
	}
}
