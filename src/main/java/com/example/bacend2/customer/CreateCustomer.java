package com.example.bacend2.customer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "https://jassisamuran.github.io")
@RequestMapping("/api")
public class CreateCustomer {

    @PostMapping("/createCustomer")
    public ResponseEntity<String> makeApiRequest(
            @RequestBody Customer customer,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader);

        // Ensure that first_name and last_name are provided
        if (customer.getFirst_name() == null || customer.getLast_name() == null) {
            return ResponseEntity.badRequest().body("Both first_name and last_name are required.");
        }

        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=create";

        RestTemplate restTemplate = new RestTemplate();

        // Create the HttpEntity with headers and body
        HttpEntity<Customer> requestEntity = new HttpEntity<>(customer, headers);

        try {
            // Make the POST request
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

            // Log response details
            System.out.println("Response Status: " + responseEntity.getStatusCode());
            System.out.println("Response Body: " + responseEntity.getBody());

            return responseEntity;
        } catch (HttpServerErrorException e) {
            // Log the server error details
            System.err.println("Server Error Status: " + e.getStatusCode());
            System.err.println("Server Error Body: " + e.getResponseBodyAsString());
            return ResponseEntity.status(e.getRawStatusCode()).body("deleted");
        } catch (HttpClientErrorException e) {
            // Log the client error details
            System.err.println("Client Error Status: " + e.getStatusCode());
            System.err.println("Client Error Body: " + e.getResponseBodyAsString());
            return ResponseEntity.status(e.getRawStatusCode()).body("deleted");
        }
    }

    @PostMapping("/deleteCustomer")
    public ResponseEntity<String> deleteApiRequest(
            @RequestParam String uuid,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader);

        // Ensure that uuid is provided
        if (uuid == null || uuid.isEmpty()) {
            return ResponseEntity.badRequest().body("UUID is required.");
        }

        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=delete&uuid=" + uuid;

        RestTemplate restTemplate = new RestTemplate();

        // Create the HttpEntity with headers
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            // Make the POST request
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

            // Log response details
            System.out.println("Response Status: " + responseEntity.getStatusCode());
            System.out.println("Response Body: " + responseEntity.getBody());

            return responseEntity;
        } catch (HttpServerErrorException e) {
            // Log the server error details
            System.err.println("Server Error Status: " + e.getStatusCode());
            System.err.println("Server Error Body: " + e.getResponseBodyAsString());
            return ResponseEntity.status(e.getRawStatusCode()).body("Error: Not deleted");
        } catch (HttpClientErrorException e) {
            // Log the client error details
            System.err.println("Client Error Status: " + e.getStatusCode());
            System.err.println("Client Error Body: " + e.getResponseBodyAsString());
            return ResponseEntity.status(e.getRawStatusCode()).body("Error: Not deleted");
        }
    }
}
