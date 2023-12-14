package com.example.bacend2.Update;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.bacend2.customer.Customer;

@RestController
@RequestMapping("/api")
public class UpdateCustomer {
	
	
	   @PostMapping("/update")
	    public ResponseEntity<String> makeApiRequest(
	            @RequestBody CustomerEdit customer,
	            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,@RequestParam String uuid){
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader);

	        // Ensure that first_name and last_name are provided
	        if (customer.getFirst_name() == null || customer.getLast_name() == null) {
	            return ResponseEntity.badRequest().body("Both first_name and last_name are required.");
	        }

	        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=update&uuid=";
	        apiUrl+=uuid;

	        RestTemplate restTemplate = new RestTemplate();

	        // Create the HttpEntity with headers and body
	       
	        HttpEntity<CustomerEdit> requestEntity = new HttpEntity<>(customer, headers);
 try{
	            // Make the POST request
	            ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);
	            int CREATED=201;
	            // Log response details
	            System.out.println("Response Status: " + responseEntity.getStatusCode());
	            System.out.println("Response Body: " + responseEntity.getBody());
	            System.out.println("all data is : "+responseEntity);
//	            HttpStatusCode d=responseEntity.getStatusCode();
	            return ResponseEntity.status(HttpStatus.CREATED).body(null);


	        } catch (HttpServerErrorException e) {
	            // Log the server error details
	            System.err.println("Server Error Status: " + e.getStatusCode());
	            System.err.println("Server Error Body: " + e.getResponseBodyAsString());
	            return ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString());
	        } catch (HttpClientErrorException e) {
	            // Log the client error details
	            System.err.println("Client Error Status: " + e.getStatusCode());
	            System.err.println("Client Error Body: " + e.getResponseBodyAsString());
	            return ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString());
	        }
	    }


	
}
