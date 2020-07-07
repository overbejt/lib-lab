package com.joverbeck.library.security;

public class SecurityConstants {
	public static final String SECRET_KEY = "Blah Blah Blah";
	public static final long MAX_TIME = 259_200_000;  // 3 days
	public static final String AUTH_PREFIX = "Bearer ";
	public static final String HEADER = "Authorization";
	public static final String SIGN_UP_URL = "/users/sign-up";
}  // End of the 'SecurityConstants' class
