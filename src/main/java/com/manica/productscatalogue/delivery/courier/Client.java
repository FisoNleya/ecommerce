package com.manica.productscatalogue.delivery.courier;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.manica.productscatalogue.auth.ValidateTokenRequest;
import com.manica.productscatalogue.auth.dtos.AuthenticationUser;
import com.manica.productscatalogue.auth.dtos.ValidUser;
import com.manica.productscatalogue.delivery.courier.request.RatesRequest;
import com.manica.productscatalogue.delivery.courier.response.RatesResponse;
import com.manica.productscatalogue.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class Client {

    private final RestTemplate restTemplate;

    @Value("${delivery.option.courier.url}")
    String courierUrl;



   public RatesResponse getRates(String bearerToken,  RatesRequest request){

       String jsonString = "{\n" +
               "    \"collection_address\": {\n" +
               "        \"type\": \"business\",\n" +
               "        \"company\": \"uAfrica.com\",\n" +
               "        \"street_address\": \"1188 Lois Avenue\",\n" +
               "        \"local_area\": \"Menlyn\",\n" +
               "        \"city\": \"Pretoria\",\n" +
               "        \"zone\": \"Gauteng\",\n" +
               "        \"country\": \"ZA\",\n" +
               "        \"code\": \"0181\",\n" +
               "        \"lat\": -25.7863272,\n" +
               "        \"lng\": 28.277583\n" +
               "    },\n" +
               "    \"delivery_address\": {\n" +
               "        \"type\": \"residential\",\n" +
               "        \"company\": \"\",\n" +
               "        \"street_address\": \"10 Midas Avenue\",\n" +
               "        \"local_area\": \"Olympus AH\",\n" +
               "        \"city\": \"Pretoria\",\n" +
               "        \"zone\": \"Gauteng\",\n" +
               "        \"country\": \"ZA\",\n" +
               "        \"code\": \"0081\",\n" +
               "        \"lat\": -25.80665579999999,\n" +
               "        \"lng\": 28.334732\n" +
               "    },\n" +
               "    \"parcels\": [\n" +
               "        {\n" +
               "            \"submitted_length_cm\": 42.5,\n" +
               "            \"submitted_width_cm\": 38.5,\n" +
               "            \"submitted_height_cm\": 5.5,\n" +
               "            \"submitted_weight_kg\": 3\n" +
               "        }\n" +
               "    ],\n" +
               "    \"declared_value\": 1500,\n" +
               "    \"collection_min_date\": \"2021-05-21\",\n" +
               "    \"delivery_min_date\": \"2021-05-21\"\n" +
               "}";


       try{


           String json = new ObjectMapper().writeValueAsString(request);
           System.out.println(json);
           request = new ObjectMapper().readValue(jsonString, RatesRequest.class);
       }catch (Exception ex) {
           log.error(ex.getMessage());
       }


       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_JSON);
       headers.set("Authorization", bearerToken);

       HttpEntity<RatesRequest> entity = new HttpEntity<>(request, headers);

      

       ResponseEntity<RatesResponse> res = restTemplate.exchange(courierUrl +
               "/rates", HttpMethod.POST, entity, RatesResponse.class);


       RatesResponse ratesResponse = res.getBody();

       if (Objects.isNull(ratesResponse)) {
           throw new DataNotFoundException("Courier guy rates not found "+ res.getBody());
       }

       log.info("Got the valid rates :" + ratesResponse);
       return ratesResponse;

   }

}
