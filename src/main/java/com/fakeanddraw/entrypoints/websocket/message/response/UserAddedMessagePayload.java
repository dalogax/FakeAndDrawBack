package com.fakeanddraw.entrypoints.websocket.message.response;

import com.fakeanddraw.entrypoints.websocket.MessagePayload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddedMessagePayload extends MessagePayload {

	private String nickname;
}
