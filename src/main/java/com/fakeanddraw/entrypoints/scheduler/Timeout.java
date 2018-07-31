package com.fakeanddraw.entrypoints.scheduler;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fakeanddraw.core.usecase.UseCase;

public class Timeout implements Runnable {

  private final Logger logger = LoggerFactory.getLogger(Timeout.class);

  private Integer matchId;
  private TimeoutType type;
  private UseCase<Integer> triggeredUseCase;

  protected Timeout(Integer matchId, TimeoutType type, UseCase<Integer> triggeredUseCase) {
    this.matchId = matchId;
    this.type = type;
    this.triggeredUseCase = triggeredUseCase;
  }

  @Override
  public void run() {
    logger.info("{} timeout {} for match {} on thread {}", new DateTime(), type, matchId,
        Thread.currentThread().getName());
    triggeredUseCase.execute(matchId);
  }
}
