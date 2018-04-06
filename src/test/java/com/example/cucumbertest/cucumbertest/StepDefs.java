package com.example.cucumbertest.cucumbertest;

import com.example.cucumbertest.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StepDefs extends SpringIntegrationTest {

    private static final String BASE_URL = "http://localhost:8089/version";
    private static final String BASE_USER_URL = "http://localhost:8089/user";

    @When("^the client calls /version$")
    public void the_client_issues_GET_version() throws Throwable {
        executeGet(BASE_URL);
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws IOException {
        HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        assertThat("status code is incorrect : " +
                latestResponse.getTheResponse().getStatusCode(), currentStatusCode.value(), is(statusCode));
        assertThat("status code is incorrect" +
                latestResponse.getTheResponse().getStatusCode(), currentStatusCode.value(), is(statusCode));
    }

    @And("^the client receives server version (.+)$")
    public void the_client_receives_server_version_body(String version) throws Throwable {
        assertThat(latestResponse.getBody(), is(version));
    }

    @When("^the client calls post request to with version (.+)$")
    public void the_client_issues_POST_version(String updatedVersion) throws Throwable {
        executePost(BASE_URL, postRequestStringParam(updatedVersion));
    }

    @Then("^the client receives POST status code of (\\d+)$")
    public void the_client_receives_status_code_post(int statusCode) throws Throwable {
        assertThat(latestReponsePost.getStatusCodeValue(), is(statusCode));
    }

    @And("^the client receives POST server version (.+)$")
    public void the_client_receives_version_body_post(String version) throws Throwable {
        assertThat(latestReponsePost.getBody(), is(version));
    }

    @When("^the client calls put request with name (.+)$")
    public void the_client_issues_PUT_user(String updatedName) throws Throwable {
        User user = new User();
        user.setName("Johnny Banansson");
        user.setAge(13);

        executePut(BASE_USER_URL, postJsonRequest(user));
    }

    @Then("^the client receives PUT status code of (\\d+)$")
    public void the_client_receives_status_code_put(int statusCode) throws IOException {
        assertThat(latestReponsePut.getStatusCodeValue(), is(statusCode));
    }

    @And("^the client will get result with name (.+)$")
    public void the_client_receives_result_name(String updatedName) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(latestReponsePut.getBody(), User.class);

        assertThat(user.getName(), is(updatedName));
    }

    @When("^the client calls delete request")
    public void the_client_issues_DELETE_user() throws Throwable {
        executeDelete(BASE_USER_URL);
    }

    @Then("^the client receives DELETE status code of (\\d+)$")
    public void the_client_receives_status_code_delete(int statusCode) throws Throwable {
        assertThat(latestReponseDelete.getStatusCodeValue(), is(statusCode));
    }

    @And("^the client will get the result of (.+)$")
    public void the_client_receives_result(String expectedMessage) throws Throwable {
        assertThat(latestReponseDelete.getBody(), is(expectedMessage));
    }
}
