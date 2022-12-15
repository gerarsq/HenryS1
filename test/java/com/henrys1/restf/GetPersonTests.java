package com.henrys1.restf;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetPersonTests {

	@Test
	public void getPersonTest() {

		RequestSpecification spec = new RequestSpecBuilder().setBaseUri("http://localhost:8083").build();

		// Set Path Variable
		spec.pathParam("personId", 1);

		// Get Person
		Response response = RestAssured.given(spec).auth().preemptive().basic("testUsername", "testPassword")
				.get("/v1/get-person/{personId}");
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
