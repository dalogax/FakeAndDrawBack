package com.fakeanddraw.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MatchService {
	
	/** Repository: games and users */
	private static Map<String, Set<String>> userRepository = new HashMap<>();
	
	/**
	 * Add an user to a game
	 * @param game game id
	 * @param user user
	 */
	public void addUser(String game, String user) {		
		Set<String> userList = userRepository.get(game);
		if (userList == null) {
			userList = new HashSet<>();
			userRepository.put(game, userList);
		}
		userList.add(user);		
	}

	/**
	 * Get all users for a game
	 * @param game game id
	 * @return user list for a game
	 */
	public List<String> getAllUsers(String game) {
		Set<String> userList = userRepository.get(game);
		if (userList != null) {
			return userList.stream().collect(Collectors.toList());
		} else {
			return new ArrayList<>();
		}
	}
}
