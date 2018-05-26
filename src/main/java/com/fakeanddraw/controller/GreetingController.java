package com.fakeanddraw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import lombok.Getter;
import lombok.Setter;

@Controller
public class GreetingController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/request")
    public void entry(Message message, SimpMessageHeaderAccessor  headerAccessor,
    		@Header("simpSessionId") String sessionId) throws Exception {
     
    	Message responseMessage = new Message();
    	
    	template.convertAndSendToUser(headerAccessor.getSessionId(), "/topic/response", message);
    }
    
    public class MessageResponse {
    	
    	@Getter @Setter String body;
    }

}
