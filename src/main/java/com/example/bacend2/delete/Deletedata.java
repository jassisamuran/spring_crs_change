package com.example.bacend2.delete;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.bacend2.customer.Customer;

@RestController
@CrossOrigin(origins = "https://jassisamuran.github.io")
@RequestMapping("/api")
public class Deletedata {

    String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=delete&uuid=";
	@PostMapping("/delete")
	public String data(@RequestParam String uuid,@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		 apiUrl+=uuid;
		    HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader);


	        RestTemplate restTemplate = new RestTemplate();

	        // Create the HttpEntity with headers and body
	        HttpEntity<Customer> requestEntity = new HttpEntity<>( headers);
	        
	            // Make the POST request
	            ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

	            // Log response details
	            System.out.println("Response Status: " + responseEntity.getStatusCode());
	            System.out.println("Response Body: " + responseEntity.getBody());

	            return "true";
	            
	}
	

	
}
