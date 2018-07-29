package com.fakeanddraw.domain.usecase;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fakeanddraw.dataproviders.repository.MatchRepository;
import com.fakeanddraw.domain.model.Match;

@Component
public class StartRound implements UseCase<Integer> {


  private final Logger logger = LoggerFactory.getLogger(StartRound.class);

  @Autowired
  private MatchRepository matchRepository;

  @Override
  public void execute(Integer matchId) {
    logger.info("StartRound triggered for match {}", matchId);

    // Get the match
    Optional<Match> optionalMatch = matchRepository.findMatchById(matchId);

    if (optionalMatch.isPresent()) {
      Match match = optionalMatch.get();

      /*
       * TODO implement logic
       */

    } else {
      logger.info("Something went wrong, match {} not found", matchId);
    }
  }
}
