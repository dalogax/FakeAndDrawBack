package com.fakeanddraw.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fakeanddraw.model.entity.Game;

public class MatchService {
	
	/** Repository: games and users */
	private static Map<Long, Set<Player>> matchRepository = new HashMap<>();
	
	/**
	 * Add an user to a game
	 * @param game game id
	 * @param user user
	 */
	public void addUser(String gameCode, String sessionId) {	
		if (game != null && game.getId() != null) {
			Set<String> userList = userRepository.get(game.getId());
			if (userList == null) {
				userList = new HashSet<>();
				userRepository.put(game.getId(), userList);
			}
			userList.add(user);			
		}
	}

	/**
	 * Get all users for a game
	 * @param game game id
	 * @return user list for a game
	 */
	public List<String> getAllUsers(Game game) {
		if (game != null && game.getId() != null) {
			Set<String> userList = userRepository.get(game.getId());
			if (userList != null) {
				return userList.stream().collect(Collectors.toList());
			}			
		}
 		return new ArrayList<>();
	}
}
