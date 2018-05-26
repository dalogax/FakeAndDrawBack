package com.fakeanddraw.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SanityCheckController {
	@RequestMapping("/")
	public String version() {
		return "Application running. Version: v0";
	}	
}
