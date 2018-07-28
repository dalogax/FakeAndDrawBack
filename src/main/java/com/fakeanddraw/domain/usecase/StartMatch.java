package com.fakeanddraw.domain.usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.fakeanddraw.entrypoints.scheduler.Timeout;

@Component
public class StartMatch implements UseCase<Integer> {


  private final Logger logger = LoggerFactory.getLogger(Timeout.class);


  @Override
  public void execute(Integer matchId) {
    // Get the match

    logger.info("startMatch triggered!");
  }
}
