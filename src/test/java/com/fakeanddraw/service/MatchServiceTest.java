package com.fakeanddraw.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fakeanddraw.model.entity.Game;

import org.junit.Assert;

public class MatchServiceTest {

	private MatchService service;
	
	@Before
	public void prepareTest() {
		service = new MatchService();
	}
	
	@Test
	public void testNullInput() {
		service.addUser(null, null);
	}
	
	@Test
	public void testAddUser() {
		Game game = new Game();
		game.setId(new Long(1));
		game.setCode("1234");
		service.addUser(game, "Pedro");
		List<String> userList = service.getAllUsers(game);
		Assert.assertTrue("El usuario Pedro si que tiene que estar", userList.contains("Pedro"));
	}
	
	@Test
	public void testMultiplesSameUser() {
		Game game = new Game();
		game.setId(new Long(1));
		game.setCode("1234");
		service.addUser(game, "Pedro");
		service.addUser(game, "Pedro");
		service.addUser(game, "Pedro");
		List<String> userList = service.getAllUsers(game);
		Assert.assertTrue("El usuario Pedro si que tiene que estar", userList.contains("Pedro"));
		Assert.assertTrue("El numero de usuarios tiene que ser uno", 1 == userList.size());
	}
	
	@Test
	public void testMultipleAddUsers() {
		Game game = new Game();
		game.setId(new Long(1));
		game.setCode("1234");		
		service.addUser(game, "Pedro");
		service.addUser(game, "Juan");
		service.addUser(game, "Dani");
		service.addUser(game, "Paula");
		List<String> userList = service.getAllUsers(game);
		Assert.assertTrue("El usuario Pedro si que tiene que estar", userList.contains("Pedro"));
		Assert.assertTrue("El usuario Dani si que tiene que estar", userList.contains("Dani"));
		Assert.assertTrue("El numero de usuarios tiene que ser cuatro", 4 == userList.size());
	}
}
