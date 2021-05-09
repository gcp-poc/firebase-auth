package com.hsbc.firebase.auth.firebaseauth.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignInInfo {
	
	private String email;
	private String password;
	private boolean returnSecureToken;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isReturnSecureToken() {
		return returnSecureToken;
	}
	public void setReturnSecureToken(boolean returnSecureToken) {
		this.returnSecureToken = returnSecureToken;
	}
	
	

}
