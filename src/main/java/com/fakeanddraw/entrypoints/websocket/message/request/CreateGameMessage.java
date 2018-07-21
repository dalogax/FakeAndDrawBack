package com.fakeanddraw.entrypoints.websocket.message.request;

import com.fakeanddraw.entrypoints.websocket.Message;


public class CreateGameMessage extends Message{
	
	public static final String messageType = "game-create";

	public CreateGameMessage(){
		super();
		this.setType(messageType);
	}
}
