package com.fakeanddraw.domain.usecase;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fakeanddraw.domain.model.Game;
import com.fakeanddraw.domain.model.Player;
import com.fakeanddraw.domain.repository.GameRepository;
import com.fakeanddraw.domain.repository.PlayerRepository;

@Component
public class AddPlayerToGame implements UseCase<AddPlayerToGameRequest,Game> {

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private PlayerRepository playerRepository;

	public Game execute(AddPlayerToGameRequest request) {
		Game game = gameRepository.findByCode(request.getRoomCode());
		if (game != null) {
			Player newPlayer = playerRepository.create(new Player(request.getPlayerSessionId(), request.getUserName()));
			if (newPlayer != null) {
				gameRepository.addPlayerToGame(game, newPlayer);
			}
		}
		return game;
	}

	private HashMap<String, Player> getPlayers(Integer gameId) {
		HashMap<String, Player> players = new HashMap<String, Player>();
		for (Player player : playerRepository.findPlayersByGame(gameId)) {
			players.put(player.getSessionId(), player);
		}
		return players;
	}
}