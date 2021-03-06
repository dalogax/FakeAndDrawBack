package com.fakeanddraw.entrypoints.websocket.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorPayload extends MessagePayload {
  Integer code;
  String message;
}
