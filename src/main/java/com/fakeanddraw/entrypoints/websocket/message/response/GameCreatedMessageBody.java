package com.fakeanddraw.entrypoints.websocket.message.response;

import java.sql.Timestamp;

import com.fakeanddraw.entrypoints.websocket.MessageBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameCreatedMessageBody extends MessageBody {

	private String gameCode;
	private Timestamp lifespanTimestamp;
}
