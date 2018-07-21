package com.fakeanddraw.entrypoints.websocket.message.response;

import com.fakeanddraw.entrypoints.websocket.MessageBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerJoinedMessageBody extends MessageBody {

	private String nickname;
}
