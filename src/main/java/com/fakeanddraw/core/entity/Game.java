package com.fakeanddraw.core.entity;

import java.util.HashMap;

import org.apache.commons.lang3.RandomStringUtils;

import lombok.Data;

@Data
public class Game{
	
	Client client;
	String code;
	HashMap<String,Player> players;

	public Game(String clientId){
		this.client = new Client(clientId);
		this.code = RandomStringUtils.randomAlphanumeric(4).toUpperCase();
		this.players = new HashMap<String,Player>();
	}
}