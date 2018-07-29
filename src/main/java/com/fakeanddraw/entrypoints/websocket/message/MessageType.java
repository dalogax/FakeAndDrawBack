package com.fakeanddraw.entrypoints.websocket.message;

import com.fakeanddraw.entrypoints.websocket.message.request.DrawingSubmitPayload;
import com.fakeanddraw.entrypoints.websocket.message.request.NewUserPayload;
import com.fakeanddraw.entrypoints.websocket.message.response.DrawingStartedPayload;
import com.fakeanddraw.entrypoints.websocket.message.response.GameCreatedPayload;
import com.fakeanddraw.entrypoints.websocket.message.response.TitleAssignPayload;
import com.fakeanddraw.entrypoints.websocket.message.response.UserAddedPayload;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MessageType {

  //@formatter:off
  CREATE_GAME("game-create", MessagePayload.class), 
  GAME_CREATED("game-created",GameCreatedPayload.class), 
  NEW_USER("new-user",NewUserPayload.class),
  USER_ADDED("user-added",UserAddedPayload.class), 
  DRAWING_STARTED("drawing-started",DrawingStartedPayload.class),
  DRAWING_SUBMIT("drawing-submit", DrawingSubmitPayload.class),
  TITLE_ASSIGN("title-assign",TitleAssignPayload.class);
  //@formatter:on

  @Getter
  private String type;

  @Getter
  private Class messagePayloadClass;

  public static MessageType findByType(String type) {
    for (MessageType t : values()) {
      if (t.getType().equals(type)) {
        return t;
      }
    }
    return null;
  }
}
