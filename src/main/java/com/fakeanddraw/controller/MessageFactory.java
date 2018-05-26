package com.fakeanddraw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fakeanddraw.controller.messages.Message;
import com.fakeanddraw.service.GameService;

@Component
public class MessageFactory {

	@Autowired
	GameService service;
	
	public void execute(Message message) {
		
		if ("game-create".equals(message)) {
			
			service.create();
			
		} else if ("new-user".equals(message.getType())) {
			
			
		} else if ("match-start".equals(message.getType())) {
			
		}
		
	}

}

