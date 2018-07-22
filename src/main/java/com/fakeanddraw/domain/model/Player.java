package com.fakeanddraw.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {

  Integer playerId;
  String sessionId;
  String userName;

  public Player(String sessionId, String userName) {
    this.sessionId = sessionId;
    this.userName = userName;
  }

}
