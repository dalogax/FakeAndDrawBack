package com.fakeanddraw.core.domain;

import java.util.HashMap;
import org.joda.time.DateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Match {

  Integer matchId;

  Game game;

  MatchStatus status;

  DateTime createdDate;
  DateTime joinTimeout;
  DateTime drawTimeout;

  HashMap<String, Player> players;
}
