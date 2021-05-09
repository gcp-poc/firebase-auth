package com.hsbc.firebase.auth.firebaseauth.service;

import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.firebase.auth.ErrorInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ImportUserRecord;
import com.google.firebase.auth.UserImportOptions;
import com.google.firebase.auth.UserImportResult;
import com.google.firebase.auth.UserProvider;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.UserRecord.UpdateRequest;
import com.google.firebase.auth.hash.HmacSha256;
import com.google.firebase.auth.hash.Pbkdf2Sha256;
import com.google.firebase.auth.hash.PbkdfSha1;
import com.hsbc.firebase.auth.firebaseauth.model.SignInInfo;
import com.hsbc.firebase.auth.firebaseauth.model.SignInResponse;

@Service
public class UserManageService {
	
	public UserRecord getUserDetailsByEmail() {
		
		try {
			UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail("amittest1@gamil.com");
			// See the UserRecord reference doc for the contents of userRecord.
			System.out.println("Successfully fetched user data: " + userRecord.getEmail());	
			return userRecord;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	
	public UserRecord createUser() {
		
		try {			
			CreateRequest request = new CreateRequest()
				    .setEmail("amittest2@gmail.com")
				    .setEmailVerified(false)
				    .setPassword("test1234")
				    .setPhoneNumber("+11234567890")
				    .setDisplayName("Amit Kumar")
				    .setPhotoUrl("http://www.example.com/12345678/photo.png")
				    .setDisabled(false);

				UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
				System.out.println("Successfully created new user: " + userRecord.getUid());
				
				return userRecord;
			
		}catch(Exception ex) {
			
		}
		return null;
	}
	
	
	public SignInResponse callSignInWithEmailAndPassword(SignInInfo signInInfo) {
		String strUrl ="https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyDYc29R_1LabV6B_boVPvbAHLEYB48DvQY";
		//URL url = new URL (strUrl);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		
		// build the request
		HttpEntity<SignInInfo> request = new HttpEntity<>(signInInfo, headers);
		
		
		System.out.println("request::"+ request);
		
		ResponseEntity<SignInResponse> responseEntity = restTemplate.postForEntity(strUrl, request, SignInResponse.class);
		
		
		
		// check response
		if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
		    System.out.println("Request Successful");
		    System.out.println(responseEntity.getBody());
		} else {
		    System.out.println("Request Failed");
		    System.out.println(responseEntity.getStatusCode());
		}
		return responseEntity.getBody();
	}
	
	
	public void importUsersWithoutPassword() {
		try {
			
			 List<ImportUserRecord> users = new ArrayList<>();
			  
			  ImportUserRecord  ImportUserRecord1 = ImportUserRecord.builder().setUid("amitwitoutpwd2")
		      .setDisplayName("Amit Witout pwd")
		      .setEmail("amitwitoutpwd2@gmail.com")			     
		      .setEmailVerified(true)
		      .setPhoneNumber("+11234567890")			   
		      .build();
			  
			  users.add(ImportUserRecord1);
			  
			  UserImportResult result = FirebaseAuth.getInstance().importUsers(users);
			  for (ErrorInfo indexedError : result.getErrors()) {
			    System.out.println("Failed to import user: " + indexedError.getReason());
			  }
			} catch (FirebaseAuthException e) {
			  System.out.println("Error importing users: " + e.getMessage());
			}
	}
	
	
	public void importUsersWithHashAndSalt() {
		try {
		String[] parts = "10000:80d5c456dc271b3823dd526f48c75eca:76a130d935d93d5d3a143956b5a1d68587e6c70652d53b6a6e91ee22c3c3614847f10cf09efe13a23097326b42a466f02497403df302ab5e4ef511caa6ed3342".split(":");
		int iterations = Integer.parseInt(parts[0]);
		byte[] salt = fromHex(parts[1]);
		byte[] hash = fromHex(parts[2]);
		
		
		
		List<ImportUserRecord> users = new ArrayList<>();
		users.add(ImportUserRecord.builder()
		    .setUid("amitwithHashAndSalt2")
		    .setEmail("amitwithHashAndSalt2@gmail.com")
		    .setPasswordHash(hash)
		    .setPasswordSalt(salt)
		    .build());
		
		
		UserImportOptions options = UserImportOptions.withHash(
				PbkdfSha1.builder()
				.setRounds(10000)
			        //.setKey("secretKey".getBytes())
			        .build());
		
		
		
		
			  UserImportResult result = FirebaseAuth.getInstance().importUsers(users, options);
			  System.out.println("Successfully imported " + result.getSuccessCount() + " users");
			  System.out.println("Failed to import " + result.getFailureCount() + " users");
			  for (ErrorInfo indexedError : result.getErrors()) {
			    System.out.println("Failed to import user at index: " + indexedError.getIndex()
			        + " due to error: " + indexedError.getReason());
			  }
			} catch (FirebaseAuthException e) {
			  // Some unrecoverable error occurred that prevented the operation from running.
				e.printStackTrace();
			}
		   catch (Exception e) {
			  // Some unrecoverable error occurred that prevented the operation from running.
			   e.printStackTrace();
			}
		
		
	}
	

   private static byte[] fromHex(String hex) {
		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < bytes.length; ++i) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}
	

}
