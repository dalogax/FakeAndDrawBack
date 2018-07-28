package com.fakeanddraw.domain.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GameFactory {

  @Value("${game.code.char.num}")
  private Integer gameCodeCharNum;

  public Game createEmptyGame() {
    return new Game();
  }

  public Game createNewGame(String sessionId) {
    Game game = new Game();
    game.setSessionId(sessionId);
    game.setGameCode(getNewGameCode());
    return game;
  }

  private String getNewGameCode() {
    return RandomStringUtils.randomAlphanumeric(gameCodeCharNum).toUpperCase();
  }
}
