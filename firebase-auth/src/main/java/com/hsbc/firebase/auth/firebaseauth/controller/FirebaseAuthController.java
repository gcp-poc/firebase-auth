package com.hsbc.firebase.auth.firebaseauth.controller;

import java.lang.reflect.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.UserRecord;
import com.hsbc.firebase.auth.firebaseauth.model.SignInInfo;
import com.hsbc.firebase.auth.firebaseauth.service.UserManageService;

@RestController
public class FirebaseAuthController {
	@Autowired
	private UserManageService userManageService;
	
	@GetMapping("/userbyemail")
	public UserRecord testFireBaseAuth() {
		return userManageService.getUserDetailsByEmail();
	}
	
	@GetMapping("/addUser")
	public UserRecord createUser() {
		return userManageService.createUser();
	}
	
	@PostMapping(path ="/signInWithEmailAndPassword")
	public UserRecord signInWithEmailAndPassword(@RequestBody SignInInfo signInInfo) {		
		System.out.println("Email Id ::"+signInInfo.getEmail());
		System.out.println("Password ::"+signInInfo.getPassword());	
		return userManageService.callSignInWithEmailAndPassword(signInInfo.getEmail(),signInInfo.getPassword());
	}
	
	@GetMapping("/importUsersWithoutPassword")
	public void importUsers() {
		userManageService.importUsersWithoutPassword();
	}
	
	
	@GetMapping("/importUsersWithHashAndSalt")
	public void importUsersWithHashAndSalt() {
		userManageService.importUsersWithHashAndSalt();
	}
	
	
	

}
