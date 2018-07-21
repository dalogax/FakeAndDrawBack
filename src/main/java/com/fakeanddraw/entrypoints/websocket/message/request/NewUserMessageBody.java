package com.fakeanddraw.entrypoints.websocket.message.request;

import com.fakeanddraw.entrypoints.websocket.MessageBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserMessageBody extends MessageBody {

	private String gameCode;
	private String nickName;
}
