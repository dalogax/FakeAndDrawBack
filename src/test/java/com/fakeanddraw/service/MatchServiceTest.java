package com.fakeanddraw.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
		service.addUser("Uno", "Pedro");
		List<String> userList = service.getAllUsers("Uno");
		Assert.assertTrue("El usuario Pedro si que tiene que estar", userList.contains("Pedro"));
	}
	
	@Test
	public void testMultiplesSameUser() {
		service.addUser("Uno", "Pedro");
		service.addUser("Uno", "Pedro");
		service.addUser("Uno", "Pedro");
		List<String> userList = service.getAllUsers("Uno");
		Assert.assertTrue("El usuario Pedro si que tiene que estar", userList.contains("Pedro"));
		Assert.assertTrue("El numero de usuarios tiene que ser uno", 1 == userList.size());
	}
	
	@Test
	public void testMultipleAddUsers() {
		service.addUser("Uno", "Pedro");
		service.addUser("Uno", "Juan");
		service.addUser("Uno", "Dani");
		service.addUser("Uno", "Paula");
		List<String> userList = service.getAllUsers("Uno");
		Assert.assertTrue("El usuario Pedro si que tiene que estar", userList.contains("Pedro"));
		Assert.assertTrue("El usuario Dani si que tiene que estar", userList.contains("Dani"));
		Assert.assertTrue("El numero de usuarios tiene que ser cuatro", 4 == userList.size());
	}
}
