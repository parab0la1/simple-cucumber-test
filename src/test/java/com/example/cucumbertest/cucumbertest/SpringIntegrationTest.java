package com.example.cucumbertest.cucumbertest;


import com.example.cucumbertest.TestNewApplication;
import com.example.cucumbertest.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ContextConfiguration(
        classes = TestNewApplication.class
)
@WebAppConfiguration
public class SpringIntegrationTest {

    static ResponseResults latestResponse = null;
    static ResponseEntity<String> latestReponsePut = null;
    static ResponseEntity<String> latestReponsePost = null;
    static ResponseEntity<String> latestReponseDelete=null;

    @Autowired
    RestTemplate restTemplate;

    void executeDelete(String url) {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");

        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        restTemplate.setErrorHandler(errorHandler);
        latestReponseDelete = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);

    }


    void executeGet(String url) throws IOException {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback(headers);
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        restTemplate.setErrorHandler(errorHandler);
        latestResponse = restTemplate.execute(url, HttpMethod.GET, requestCallback, response -> {
            if (errorHandler.hadError) {
                return (errorHandler.getResults());
            } else {
                return (new ResponseResults(response));
            }
        });
    }

    void executePut(String url, HttpEntity<?> requestEntity) throws IOException {

        final Map<String, String> headers = new HashMap<>();
        ResponseEntity<String> restTemplateExchange = null;
        headers.put("Accept", "application/json");
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }

        restTemplate.setErrorHandler(errorHandler);

        latestReponsePut = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
    }

    void executePost(String url, HttpEntity<?> requestEntity) throws IOException {

        System.out.println(":" + requestEntity);
        final Map<String, String> headers = new HashMap<>();
        ResponseEntity<String> restTemplateExchange = null;
        headers.put("Accept", "application/json");
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler();

        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }

        restTemplate.setErrorHandler(errorHandler);

        latestReponsePost = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }

    HttpEntity<?> postJsonRequest(User user) throws JSONException, JsonProcessingException {
        HttpHeaders headers2 = new HttpHeaders();
        headers2.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(user);

        return new HttpEntity<>(jsonInString, headers2);
    }

    HttpEntity<?> postRequestStringParam(String test) throws JSONException {
        HttpHeaders headers2 = new HttpHeaders();
        headers2.setContentType(MediaType.APPLICATION_JSON);
        headers2.setContentType(MediaType.TEXT_PLAIN);

        return new HttpEntity<>(test, headers2);
    }
}
