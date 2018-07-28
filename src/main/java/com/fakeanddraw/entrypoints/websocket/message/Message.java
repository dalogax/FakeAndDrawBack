package com.fakeanddraw.entrypoints.websocket.message;

import java.util.Map;
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
  private Boolean error = false;

  public Message(String type, MessagePayload messagePayload) {
    super();
    this.type = type;
    this.setMessagePayload(messagePayload);
    if (messagePayload instanceof ErrorPayload) {
      this.setError(true);
    }
  }

  @JsonIgnore
  public MessagePayload getMessagePayload() {
    return (MessagePayload) new ObjectMapper().convertValue(this.payload,
        MessageType.findByType(this.type).getMessagePayloadClass());
  }

  @JsonIgnore
  public void setMessagePayload(MessagePayload messagePayload) {

    this.payload = new ObjectMapper().convertValue(messagePayload, Map.class);
  }
}
