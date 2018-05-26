package com.fakeanddraw.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fakeanddraw.model.entity.Game;
import com.fakeanddraw.util.AppHelper;

@Service
public class GameService {
	
	// temporary repository
	private static List<String> gameRepository = new ArrayList<String>();
	
	/**
	 * Inserts a new Game register, generating the new code
	 * @return game object
	 */
	public Game create (){

		Game game = new Game();
		
		String code = AppHelper.getRandomString(6);
		game.setId(Long.valueOf(gameRepository.size()));
		game.setCode(code);
		
		gameRepository.add(code);
		
		return game;
	}
}
