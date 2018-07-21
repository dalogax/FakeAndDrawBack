package com.fakeanddraw.entrypoints.websocket.message;

public enum MessageType {
	CREATE_GAME("game-create"),
	GAME_CREATED("game-created"),
	NEW_USER("new-user"),
	USER_ADDED("user-added");
	
	private String type;
	
	private MessageType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	public static MessageType findByType(String type){
	    for(MessageType t : values()){
	        if( t.getType().equals(type)){
	            return t;
	        }
	    }
	    return null;
	}
}
