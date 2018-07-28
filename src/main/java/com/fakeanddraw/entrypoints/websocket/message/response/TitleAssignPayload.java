package com.fakeanddraw.entrypoints.websocket.message.response;

import java.sql.Timestamp;
import com.fakeanddraw.entrypoints.websocket.message.MessagePayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TitleAssignPayload extends MessagePayload {

  private Timestamp lifespanTimestamp;
  private String title;
}
