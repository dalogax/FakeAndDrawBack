package com.fakeanddraw.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fakeanddraw.domain.model.Game;
import com.fakeanddraw.domain.repository.GameRepository;

@Component
public class CreateGame implements UseCase<String,Game> {

	@Autowired
	private GameRepository gameRepository;

	@Override
	public Game execute(String sessionId) {
		return gameRepository.create(new Game(sessionId));
	}
}