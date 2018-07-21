package com.fakeanddraw.domain.usecase;

import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fakeanddraw.domain.model.Game;
import com.fakeanddraw.domain.repository.GameRepository;
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

	@Override
	public void execute(String sessionId) {
		Game newGame = gameRepository.create(new Game(sessionId));
		
		DateTime waitingRoomTimeout = new DateTime();
		waitingRoomTimeout = waitingRoomTimeout.plusSeconds(15);

		Message gameCreatedMessage = new Message(MessageType.GAME_CREATED.getType(),
				new GameCreatedMessagePayload(newGame.getRoomCode(), new Timestamp(waitingRoomTimeout.getMillis())));

		// Notify master with room code
		responseController.send(sessionId,gameCreatedMessage);
	}
}