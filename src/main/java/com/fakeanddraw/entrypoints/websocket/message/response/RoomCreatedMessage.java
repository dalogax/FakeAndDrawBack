package com.fakeanddraw.entrypoints.websocket.message.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomCreatedMessage {

	private String roomCode;
}
