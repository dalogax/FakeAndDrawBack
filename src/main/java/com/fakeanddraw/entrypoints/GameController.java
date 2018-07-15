package com.fakeanddraw.entrypoints;

import java.util.HashMap;
import java.util.Map;

import com.fakeanddraw.core.entity.Game;
import com.fakeanddraw.core.entity.Player;
import com.fakeanddraw.entrypoints.message.JoinMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    public static Map<String,Game> games = new HashMap<String,Game>();

    @MessageMapping("/create")
    public void create(SimpMessageHeaderAccessor  headerAccessor,
    		@Header("simpSessionId") String sessionId) throws Exception {
        
        //Create new game and add to game repo
        Game game = new Game(headerAccessor.getSessionId());
        games.put(game.getCode(), game);

        //Notify master with room code
        template.convertAndSendToUser(headerAccessor.getSessionId(), "/topic/roomCreated", "{\"roomCode\":\"" + game.getCode() + "\"}");
    }

    @MessageMapping("/join")
    public void join(JoinMessage message, SimpMessageHeaderAccessor  headerAccessor,
    		@Header("simpSessionId") String sessionId) throws Exception {       

        if (games.containsKey(message.getRoomCode())){

            Game game = games.get(message.getRoomCode());
            //Update game with the new player
            game.getPlayers().put(headerAccessor.getSessionId(),new Player(headerAccessor.getSessionId(),message.getName()));
            //Notify master about new user joined
            template.convertAndSendToUser(game.getClient().getClientId(), "/topic/playerJoined", "{\"user\":\"" + message.getName() + "\"}");	
        }
    }
    
    @Autowired
    private SimpMessagingTemplate template; 
}
