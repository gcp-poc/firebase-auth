package com.hsbc.firebase.auth.firebaseauth.service;


import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FirebaseAuthService {

	@PostConstruct
	public void initFireBaseConfig() {
		
		try {
			InputStream serviceAccount = this.getClass().getClassLoader().
					getResourceAsStream("./fir-poc-c6dad-firebase-adminsdk-5ttj1-bd689db374.json");
					

			FirebaseOptions options = new FirebaseOptions.Builder()
					  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
					  .build();
			
			if(FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}
		}catch(Exception ex) {
			System.out.println(" exception::"+ ex.getStackTrace());
			ex.printStackTrace();
		}
	}
	
	
	

}
