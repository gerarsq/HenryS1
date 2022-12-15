package com.henrys1.restf;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostPersonTests {

	@Test
	public void postPersonTest() {
		
		// Create JSON body
		JSONObject body = new JSONObject();
		body.put("firstName", "Gera");
		body.put("lastName", "San");
		body.put("phoneNumber", "22887766");

		RequestSpecification spec = new RequestSpecBuilder().setBaseUri("http://localhost:8083").build();
		
		// Create Person
		Response response = RestAssured.given(spec).auth().preemptive().basic("admin", "testPassword")
				.contentType(ContentType.JSON).body(body.toString()).post("/v1/post-person");
		response.print();

		// Verify response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 and it is not");

		// Verify all fields
		SoftAssert softAssert = new SoftAssert();
		String actualFirstName = response.jsonPath().getString("firstName");
		softAssert.assertEquals(actualFirstName, "Gera", "firstName in response is not expected");

		String actualLastName = response.jsonPath().getString("lastName");
		softAssert.assertEquals(actualLastName, "San", "lastName in response is not expected");

		String actualPhone = response.jsonPath().getString("phoneNumber");
		softAssert.assertEquals(actualPhone, "22887766", "phoneNumber in response is not expected");

		softAssert.assertAll();

	}
}
