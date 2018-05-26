package com.fakeanddraw.util;

import org.apache.commons.lang3.RandomStringUtils;

public class AppHelper {


	public static String getRandomString(int length) {
	    String generatedString = RandomStringUtils.randomAlphabetic(length);

	    return generatedString;
	}
}
