package com.fakeanddraw.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MatchService {
	
	/** Repositorio de juegos y usuarios */
	private static Map<String, Set<String>> userRepository = new HashMap<>();
	
	public void addUser(String game, String user) {		
		Set<String> userList = userRepository.get(game);
		if (userList == null) {
			userList = new HashSet<>();
			userRepository.put(game, userList);
		}
		userList.add(user);		
	}
}
