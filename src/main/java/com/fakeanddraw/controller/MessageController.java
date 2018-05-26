package com.fakeanddraw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fakeanddraw.controller.messages.Message;

@Controller
public class MessageController {

	
    @Autowired
    private SimpMessagingTemplate template;
    
    @Autowired
    private MessageFactory factory;

    @MessageMapping("/request")
    public void entry(Message message, SimpMessageHeaderAccessor  headerAccessor,
    		@Header("simpSessionId") String sessionId) throws Exception {
     
    	factory.execute(message);
    	
    	template.convertAndSendToUser(headerAccessor.getSessionId(), "/topic/response", message);
    }
    

}
