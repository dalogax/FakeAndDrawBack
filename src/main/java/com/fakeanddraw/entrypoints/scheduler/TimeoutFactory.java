package com.fakeanddraw.entrypoints.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fakeanddraw.domain.usecase.StartMatch;

@Component
public class TimeoutFactory {

  @Autowired
  StartMatch startMatch;

  public Timeout createTimeout(TimeoutType type, Integer matchId) {
    switch (type) {
      case JOIN:
        return new Timeout(matchId, type, startMatch);

      default:
        return null;
    }
  }
}
