package com.fakeanddraw.entrypoints.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fakeanddraw.entrypoints.websocket.message.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ResponseController {
	
	@Autowired
	private SimpMessagingTemplate template;
	
	private final Logger logger = LoggerFactory.getLogger(ResponseController.class);
	
	public void send(String sessionId, Message response) {
		try {
			template.convertAndSendToUser(sessionId, "/response",
					new ObjectMapper().writeValueAsString(response));
			logger.info("Sending message to client -> sessionId:{}, message: {}", sessionId, response);
		} catch (Exception e) {
			logger.error("Error sending message to client -> sessionId:" + sessionId + ", message: {}" + response,e);
		}
	}
}
