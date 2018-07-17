package com.fakeanddraw.domain.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fakeanddraw.domain.model.Game;
import com.fakeanddraw.domain.model.Player;
import com.fakeanddraw.domain.repository.GameRepository;
import com.fakeanddraw.domain.repository.PlayerRepository;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private PlayerRepository playerRepository;

	public Game createGame(String sessionId) {
		return gameRepository.create(new Game(sessionId));
	}

	public Game addPlayer(String roomCode, String playerSessionId, String userName) {
		Game game = gameRepository.findByCode(roomCode);
		if (game != null) {
			Player newPlayer = playerRepository.create(new Player(playerSessionId, userName));
			if (newPlayer != null) {
				gameRepository.addPlayerToGame(game, newPlayer);
			}
		}
		return game;
	}

	public HashMap<String, Player> getPlayers(Integer gameId) {
		HashMap<String, Player> players = new HashMap<String, Player>();
		for (Player player : playerRepository.findPlayersByGame(gameId)) {
			players.put(player.getSessionId(), player);
		}
		return players;
	}
}