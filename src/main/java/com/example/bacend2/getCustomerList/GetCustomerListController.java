package com.example.bacend2.getCustomerList;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController	
@CrossOrigin(origins = "https://jassisamuran.github.io")
@RequestMapping("/api")
public class GetCustomerListController {

    private final String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp";

    @GetMapping("/getCustomerList")
    public ResponseEntity<List<Customer>> getCustomerList(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader);
        headers.setAccept(Collections.singletonList(MediaType.ALL)); // Accept any content type

        // Configure RestTemplate with MappingJackson2HttpMessageConverter
        RestTemplate restTemplate = new RestTemplate();

        // Parameters for the GET request
        String apiUrlWithParams = apiUrl + "?cmd=get_customer_list";

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            // Make the GET request
        	MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        	restTemplate.getMessageConverters().add(converter);

            ResponseEntity<Customer[]> responseEntity = restTemplate.exchange(
                    apiUrlWithParams,
                    HttpMethod.GET,
                    entity,
                    Customer[].class);

            // Convert the array to a List
            List<Customer> customerList = List.of(responseEntity.getBody());

            // Log response details
            System.out.println("Response Status: " + responseEntity.getStatusCode());
            System.out.println("Response Body: " + customerList);

            return ResponseEntity.ok(customerList);
        } catch (HttpClientErrorException.Unauthorized unauthorized) {
            // Log the 401 error
            System.err.println("Error: 401 Unauthorized");
            System.err.println("Error Body: " + unauthorized.getResponseBodyAsString());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            // Log other errors
            System.err.println("Error: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
