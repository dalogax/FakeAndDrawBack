package com.fakeanddraw.demo;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {
	
	public static Set<String> sessionList = new HashSet<>();

    @MessageMapping("/hello")
    public Greeting greeting(HelloMessage message, SimpMessageHeaderAccessor  headerAccessor,
    		@Header("simpSessionId") String sessionId) throws Exception {
    	
    	sessionList.add(headerAccessor.getSessionId());
    	
    	for (String candidate: sessionList) {
    		if ( ! sessionId.equals(candidate)) {
    			template.convertAndSendToUser(candidate, "/topic/greetings", "{\"body\":\"Hello2 " + message.getName() + "\"}");		
    		}
    	}
    	    	
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
    
    @Autowired
    private SimpMessagingTemplate template;
//
//    @Scheduled(fixedRate = 5000)
//    public void greeting() throws Exception {
//        Thread.sleep(1000); // simulated delay
//        System.out.println("scheduled");
//        this.template.convertAndSendToUser("1", "/topic/greetings", "{\"body\":\"Hello\"}");
//    }    
}
