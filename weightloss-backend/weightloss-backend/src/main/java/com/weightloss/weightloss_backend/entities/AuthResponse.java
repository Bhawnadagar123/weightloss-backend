package com.weightloss.weightloss_backend.entities;

public class AuthResponse {
	private String accessToken;
    private String tokenType = "Bearer";
    private Long expiresInMs;
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public Long getExpiresInMs() {
		return expiresInMs;
	}
	public void setExpiresInMs(Long expiresInMs) {
		this.expiresInMs = expiresInMs;
	}
	public AuthResponse(String accessToken, String tokenType, Long expiresInMs) {
		super();
		this.accessToken = accessToken;
		this.tokenType = tokenType;
		this.expiresInMs = expiresInMs;
	}
	public AuthResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
}
