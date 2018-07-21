package com.fakeanddraw.entrypoints.websocket.message.response;

import com.fakeanddraw.entrypoints.websocket.Message;
import com.fakeanddraw.entrypoints.websocket.MessageBody;


public class GameCreatedMessage extends Message{
	
	public static final String messageType = "game-created";

	public GameCreatedMessage(MessageBody body){
		super();
		this.setType(messageType);
		this.setBody(body);
	}
}
