package com.fakeanddraw.entrypoints.websocket.message.request;

import com.fakeanddraw.entrypoints.websocket.Message;


public class NewUserMessage extends Message{
	
	public static final String messageType = "new-user";

	public NewUserMessage(){
		super();
		this.setType(messageType);
	}
}
