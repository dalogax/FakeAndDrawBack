package com.fakeanddraw.entrypoints.websocket.message.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinGameMessage {

	private String roomCode;
	private String name;
}
