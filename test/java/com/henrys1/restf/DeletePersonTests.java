package com.henrys1.restf;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DeletePersonTests {

	@Test
	public void deletePersonTest() {

		RequestSpecification spec = new RequestSpecBuilder().setBaseUri("http://localhost:8083").build();
		
		// Set Path Variable
		spec.pathParam("personId", 1);
		
		// Delete Person
		Response response = RestAssured.given(spec).auth().preemptive().basic("admin", "testPassword")
				.delete("/v1/delete-person/{personId}");
		response.print();

		// Verify response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 and it is not");
		
		// Verify Person object has been removed
		Response responseGet = RestAssured.given(spec).auth().preemptive().basic("testUsername", "testPassword")
				.get("/v1/get-person/{personId}");
		responseGet.print();
		
		Assert.assertEquals(responseGet.getBody().asString(), "Not Found", "Should be Not Found and it is not");

	}

}
