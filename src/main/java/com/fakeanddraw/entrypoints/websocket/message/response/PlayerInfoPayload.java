package com.fakeanddraw.entrypoints.websocket.message.response;

import com.fakeanddraw.entrypoints.websocket.message.MessagePayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerInfoPayload extends MessagePayload {
  private Integer userId;
  private String nickname;
}
