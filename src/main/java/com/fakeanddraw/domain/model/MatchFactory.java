package com.fakeanddraw.domain.model;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MatchFactory {

  @Value("${game.timeout.join.seconds}")
  Integer joinTimeoutSeconds;

  @Value("${game.timeout.draw.seconds}")
  Integer drawTimeoutSeconds;

  public Match createEmptyMatch() {
    return new Match();
  }

  public Match createNewMatch(Game game) {
    Match match = new Match();
    match.setGame(game);
    match.setStatus(MatchStatus.JOIN);
    match.setCreatedDate(new DateTime());
    match.setJoinTimeout(match.getCreatedDate().plusSeconds(joinTimeoutSeconds));
    match.setDrawTimeout(match.getJoinTimeout().plusSeconds(drawTimeoutSeconds));
    return match;
  }
}
