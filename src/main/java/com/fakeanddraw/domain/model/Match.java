package com.fakeanddraw.domain.model;

import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match {

  Integer matchId;

  Game game;

  MatchStatus status;

  HashMap<String, Player> players;

  public Match(Game game) {
    this.game = game;
    this.status = MatchStatus.JOIN;
    this.players = new HashMap<String, Player>();
  }

}
