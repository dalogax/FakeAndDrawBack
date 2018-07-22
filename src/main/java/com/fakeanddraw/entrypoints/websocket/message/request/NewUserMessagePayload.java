package com.fakeanddraw.entrypoints.websocket.message.request;

import com.fakeanddraw.entrypoints.websocket.message.MessagePayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserMessagePayload extends MessagePayload {

  private String nickname;
  private String gameCode;
}
