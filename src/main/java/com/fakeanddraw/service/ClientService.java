package com.fakeanddraw.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fakeanddraw.model.entity.Client;
import com.fakeanddraw.model.entity.Game;
import com.fakeanddraw.util.AppHelper;

import org.springframework.stereotype.Service;

@Service
public class ClientService {
	
	// temporary repository
	private static Map<String,Client> clientRepository = new HashMap<String,Client>();
	
	/**
	 * Inserts a new Game register, generating the new code
	 * @return game object
	 */
	public Client registerClient (String sessionId){
		Client client = null;
		if (clientRepository.containsKey(sessionId)){
			client = clientRepository.get(sessionId);
		} else {
			client = new Client();
			client.setSessionId(sessionId);
			client.setId(clientRepository.size());
			clientRepository.put(sessionId,client);
		}
		return client;
	}

	public Client getClient(String sessionId){
		return this.clientRepository.get(sessionId);
	}
}
