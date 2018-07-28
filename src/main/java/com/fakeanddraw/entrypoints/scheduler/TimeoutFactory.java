package com.fakeanddraw.entrypoints.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fakeanddraw.domain.usecase.StartMatch;
import com.fakeanddraw.domain.usecase.StartRound;

@Component
public class TimeoutFactory {

  @Autowired
  private StartMatch startMatch;

  @Autowired
  private StartRound startRound;

  public Timeout createTimeout(TimeoutType type, Integer matchId) {
    switch (type) {
      case JOIN:
        return new Timeout(matchId, type, startMatch);
      case DRAW:
        return new Timeout(matchId, type, startRound);
      default:
        return null;
    }
  }
}
