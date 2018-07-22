package com.fakeanddraw.entrypoints.websocket.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MessageType {
  CREATE_GAME("game-create"), GAME_CREATED("game-created"), NEW_USER("new-user"), USER_ADDED(
      "user-added");

  @Getter
  private String type;

  public static MessageType findByType(String type) {
    for (MessageType t : values()) {
      if (t.getType().equals(type)) {
        return t;
      }
    }
    return null;
  }
}
