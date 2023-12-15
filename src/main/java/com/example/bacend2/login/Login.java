package com.example.bacend2.login;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class Login {
	
	private final String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";

@CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity<String> makeApiRequest(@RequestBody LoginData requestData) {
            HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setAccessControlAllowOrigin("*");
	    // Create the request body
	    String requestBody = String.format("{\"login_id\": \"%s\", \"password\": \"%s\"}",
	            requestData.getLogin_id(), requestData.getPassword());
	
	    // Create the HttpEntity with headers and body
	    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
	
	    // Make the POST request
	    RestTemplate restTemplate = new RestTemplate();
	    try {
	        ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
	        return ResponseEntity.status(200).headers(headers).body(requestEntity.getBody());
	    } catch (Exception e) {
	        return ResponseEntity.status(500).headers(headers).body(e.getMessage());
	    }
	}
    }
	@PostMapping("/now")
	  public ResponseEntity<String> handle(@RequestBody LoginData loginData ) {
		
        return ResponseEntity.ok("{\"message\": \"Login successful!\"}"+loginData.getLogin_id()+" "+loginData.getPassword());
        
    	
    }
	@PostMapping("/ni")
	  public ResponseEntity<String> hand(@RequestBody LoginData loginData ) {
		
      return ResponseEntity.ok("{\"message\": \"Login successful!\"}"+loginData.getLogin_id()+" "+loginData.getPassword());
      
  	
  }

}
