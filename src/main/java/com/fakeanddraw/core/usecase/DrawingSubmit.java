package com.fakeanddraw.core.usecase;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fakeanddraw.core.domain.Title;
import com.fakeanddraw.dataproviders.repository.DrawingRepository;
import com.fakeanddraw.dataproviders.repository.TitleRepository;
import com.fakeanddraw.entrypoints.websocket.ResponseController;
import com.fakeanddraw.entrypoints.websocket.message.Message;
import com.fakeanddraw.entrypoints.websocket.message.MessageType;
import com.fakeanddraw.entrypoints.websocket.message.response.PlayerInfoPayload;
import javassist.NotFoundException;

@Component
public class DrawingSubmit implements UseCase<DrawingSubmitRequest> {

  private Logger logger = LoggerFactory.getLogger(DrawingSubmit.class);

  @Autowired
  private TitleRepository titleRepository;

  @Autowired
  private DrawingRepository drawingRepository;

  @Autowired
  private ResponseController responseController;

  @Override
  public void execute(DrawingSubmitRequest request) {

    Optional<Title> optionalTitle =
        titleRepository.findCurrentTitleByPlayerSessionId(request.getSessionId());
    if (optionalTitle.isPresent()) {
      // Save the image received
      optionalTitle.get().getDrawing().setImage(request.getImage());
      try {
        drawingRepository.update(optionalTitle.get().getDrawing());
      } catch (NotFoundException e) {
        logger.error(
            "Something very weird happened. We failed updating a drawing we just got. DrawingId {}",
            optionalTitle.get().getDrawing().getDrawingId());
      }

      // Send message to master: an user has submitted an image
      Message drawingAddedMessage = new Message(MessageType.DRAWING_ADDED.getType(),
          new PlayerInfoPayload(optionalTitle.get().getPlayer().getPlayerId(),
              optionalTitle.get().getPlayer().getUserName()));

      responseController.send(optionalTitle.get().getDrawing().getMatch().getGame().getSessionId(),
          drawingAddedMessage);

      /*
       * TODO Check if all images have been submitted. In that case jump to start round
       */

    }
  }
}
