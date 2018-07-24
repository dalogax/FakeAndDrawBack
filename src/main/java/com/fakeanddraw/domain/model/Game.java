package com.fakeanddraw.domain.model;

import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Game {

  Integer gameId;
  String sessionId;
  String gameCode;
  List<Match> matches;
}
