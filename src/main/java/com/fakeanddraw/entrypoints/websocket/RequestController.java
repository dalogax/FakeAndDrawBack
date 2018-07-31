package com.fakeanddraw.entrypoints.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import com.fakeanddraw.core.usecase.AddPlayerToGame;
import com.fakeanddraw.core.usecase.AddPlayerToGameRequest;
import com.fakeanddraw.core.usecase.CreateGame;
import com.fakeanddraw.core.usecase.DrawingSubmit;
import com.fakeanddraw.core.usecase.DrawingSubmitRequest;
import com.fakeanddraw.entrypoints.websocket.message.Message;
import com.fakeanddraw.entrypoints.websocket.message.MessageType;
import com.fakeanddraw.entrypoints.websocket.message.request.DrawingSubmitPayload;
import com.fakeanddraw.entrypoints.websocket.message.request.NewUserPayload;

@Controller
public class RequestController {

  @Autowired
  private CreateGame createGame;

  @Autowired
  private AddPlayerToGame addPlayerToGame;

  @Autowired
  private DrawingSubmit drawingSubmit;

  private final Logger logger = LoggerFactory.getLogger(RequestController.class);

  @MessageMapping("/request")
  public void request(Message message, @Header("simpSessionId") String sessionId) {

    if (message != null && message.getType() != null) {
      switch (MessageType.findByType(message.getType())) {
        case CREATE_GAME:
          createGame.execute(sessionId);
          break;

        case NEW_USER:
          NewUserPayload newUserMessagePayload = (NewUserPayload) message.getMessagePayload();

          addPlayerToGame.execute(new AddPlayerToGameRequest(newUserMessagePayload.getGameCode(),
              sessionId, newUserMessagePayload.getNickname()));

          break;

        case DRAWING_SUBMIT:
          DrawingSubmitPayload drawingSubmitPayload =
              (DrawingSubmitPayload) message.getMessagePayload();

          drawingSubmit
              .execute(new DrawingSubmitRequest(sessionId, drawingSubmitPayload.getImage()));
          break;
          
        default:
          logger.error("Message not supported received: {}", message);
          break;
      }
    }
  }
}
