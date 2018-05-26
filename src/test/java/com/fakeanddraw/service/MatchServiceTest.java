package com.fakeanddraw.service;

import org.junit.Before;
import org.junit.Test;

public class MatchServiceTest {

	private MatchService service;
	
	@Before
	public void prepareTest() {
		service = new MatchService();
	}
	
	@Test
	public void testNullInput() {
		service.addUser();
	}
	
	
}
