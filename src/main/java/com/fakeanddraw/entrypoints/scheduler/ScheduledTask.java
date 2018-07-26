package com.fakeanddraw.entrypoints.scheduler;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduledTask implements Runnable {

  private final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

  private String message;

  public ScheduledTask(String message) {
    this.message = message;
  }

  @Override
  public void run() {
    logger.info(new DateTime() + " Scheduled Task with " + message + " on thread "
        + Thread.currentThread().getName());
  }
}
