package com.fakeanddraw.entrypoints.websocket.message.response;

import java.sql.Timestamp;
import com.fakeanddraw.entrypoints.websocket.message.MessagePayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameCreatedMessagePayload extends MessagePayload {

  private String gameCode;
  private Timestamp lifespanTimestamp;
}
