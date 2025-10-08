package com.weightloss.weightloss_backend.entities;

public class LoginResponse {

	private String token;

	public LoginResponse(String token) {
		super();
		this.token = token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

}
