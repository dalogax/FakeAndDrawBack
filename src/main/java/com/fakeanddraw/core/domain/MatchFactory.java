package com.fakeanddraw.core.domain;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MatchFactory {

  @Value("${game.timeout.join.seconds}")
  private Integer joinTimeoutSeconds;

  @Value("${game.timeout.draw.seconds}")
  private Integer drawTimeoutSeconds;

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
