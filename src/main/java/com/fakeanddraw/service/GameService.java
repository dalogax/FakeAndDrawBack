package com.fakeanddraw.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fakeanddraw.model.entity.Game;
import com.fakeanddraw.model.entity.Match;
import com.fakeanddraw.util.AppHelper;

@Service
public class GameService {
	
	// temporary repository
	private static Map<String,Game> gameRepository = new HashMap<String,Game>();

	@Autowired
	private ClientService clientService;
	
	/**
	 * Inserts a new Game register, generating the new code
	 * @return game object
	 */
	public Game create (String sessionId){
		Game game = new Game();
		game.setMaster(clientService.getClient(sessionId));
		String code = AppHelper.getRandomString(6);
		game.setId(Long.valueOf(gameRepository.size()));
		game.setCode(code);
		
		gameRepository.put(code,game);
		
		return game;
	}
}
