package com.fakeanddraw.domain.model;

import java.util.HashMap;

import org.apache.commons.lang3.RandomStringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {

	Integer gameId;
	String sessionId;
	String roomCode;

	HashMap<String, Player> players;

	public Game(String sessionId) {
		this.sessionId = sessionId;
		this.roomCode = getNewRoomCode();
		this.players = new HashMap<String, Player>();
	}

	public static String getNewRoomCode() {
		return RandomStringUtils.randomAlphanumeric(4).toUpperCase();
	}

}