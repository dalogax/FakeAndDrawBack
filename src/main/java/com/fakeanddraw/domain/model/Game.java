package com.fakeanddraw.domain.model;

import java.util.ArrayList;
import java.util.List;
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
  String gameCode;
  List<Match> matches;

  public Game(String sessionId) {
    this.sessionId = sessionId;
    this.gameCode = getNewRoomCode();
    this.matches = new ArrayList<Match>();
  }

  public static String getNewRoomCode() {
    return RandomStringUtils.randomAlphanumeric(4).toUpperCase();
  }
}
