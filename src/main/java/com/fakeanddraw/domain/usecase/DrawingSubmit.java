package com.fakeanddraw.domain.usecase;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fakeanddraw.dataproviders.repository.DrawingRepository;
import com.fakeanddraw.dataproviders.repository.MatchRepository;
import com.fakeanddraw.domain.model.Drawing;
import com.fakeanddraw.domain.model.Match;
import javassist.NotFoundException;

@Component
public class DrawingSubmit implements UseCase<DrawingSubmitRequest> {

  private Logger logger = LoggerFactory.getLogger(DrawingSubmit.class);

  @Autowired
  private MatchRepository matchRepository;

  @Autowired
  private DrawingRepository drawingRepository;

  @Override
  public void execute(DrawingSubmitRequest request) {

    Optional<Match> match = matchRepository.findLastMatchByPlayerSessionId(request.getSessionId());
    Optional<Drawing> drawing = drawingRepository.findActiveDrawingByPlayerSessionIdAndMatchId(
        request.getSessionId(), match.get().getMatchId());

    drawing.get().setImage(request.getBase64Image());

    try {
      drawingRepository.update(drawing.get());
    } catch (NotFoundException e) {
      logger.warn("The empty drawing does not exists for the player session id: {} and matchId: {}",
          request.getSessionId(), match.get().getMatchId());
      return;
    }

    // TODO Send message to master: an user has submitted an image
    // TODO Check if all images have been submitted. If yes, close this round
    // sending the proper message to master
  }
}
