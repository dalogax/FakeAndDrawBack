package com.fakeanddraw.entrypoints.websocket.message;

import java.util.Map;
import com.fakeanddraw.entrypoints.websocket.message.request.NewUserMessagePayload;
import com.fakeanddraw.entrypoints.websocket.message.response.GameCreatedMessagePayload;
import com.fakeanddraw.entrypoints.websocket.message.response.UserAddedMessagePayload;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

  private String type;
  private Map<String, Object> payload;
  private Boolean error;

  public Message(String type, MessagePayload messagePayload) {
    super();
    this.type = type;
    this.setMessagePayload(messagePayload);
    if (messagePayload instanceof ErrorMessagePayload) {
      this.setError(true);
    }
  }

  @JsonIgnore
  public MessagePayload getMessagePayload() {
    MessagePayload messagePayload = null;
    switch (MessageType.findByType(this.type)) {
      case CREATE_GAME:
        messagePayload = new ObjectMapper().convertValue(this.payload, MessagePayload.class);
        break;
      case GAME_CREATED:
        messagePayload =
            new ObjectMapper().convertValue(this.payload, GameCreatedMessagePayload.class);
        break;
      case NEW_USER:
        messagePayload = new ObjectMapper().convertValue(this.payload, NewUserMessagePayload.class);
        break;
      case USER_ADDED:
        messagePayload =
            new ObjectMapper().convertValue(this.payload, UserAddedMessagePayload.class);
        break;
    }
    return messagePayload;
  }

  @JsonIgnore
  public void setMessagePayload(MessagePayload messagePayload) {

    this.payload = new ObjectMapper().convertValue(messagePayload, Map.class);
  }
}
