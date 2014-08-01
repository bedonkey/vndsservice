package vn.com.vndirect.api.rest;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import groovyx.net.http.ContentType;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import vn.com.vndirect.api.model.Order;
import com.jayway.restassured.RestAssured;

public class OrderServiceTest {
	
	@Before
	public void setUp(){
		RestAssured.basePath = "http://localhost:8080/vndsService";
	}
	
	@Test
	public void helloTest() {
		expect().
	    statusCode(200).
	    contentType(ContentType.JSON).
	    when().
	    get("/rest/hello/giangnt");
	}
	
	@Test
	public void placeOrderTestSucess () {
		given().
		contentType(ContentType.JSON).
		formParams("id", "123", "account", "123456", "symbol", "VND", "price", 10000, "quantity", 1000, "orderType", "LO").
		expect().
	    statusCode(200).
	    body(equalTo("123")).
	    when().
	    post("/rest/order/place");
	}
	
	@Test
	public void placeOrderTestFailWithEmptyAccount () {
		given().
		contentType(ContentType.JSON).
		formParams("id", "123", "account", "", "symbol", "VND", "price", 10000, "quantity", 1000, "orderType", "LO").
		expect().
	    statusCode(200).
	    body(equalTo("Account Empty")).
	    when().
	    post("/rest/order/place");
	}
	
	@Test
	public void placeOrderTestFailWithEmptySymbol () {
		given().
		contentType(ContentType.JSON).
		formParams("id", "123", "account", "123456", "symbol", "", "price", 10000, "quantity", 1000, "orderType", "LO").
		expect().
	    statusCode(200).
	    body(equalTo("Symbol Empty")).
	    when().
	    post("/rest/order/place");
	}
	
	@Test
	public void placeOrderTestFailWithPriceLowerThanZezo () {
		given().
		contentType(ContentType.JSON).
		formParams("id", "123", "account", "123456", "symbol", "VND", "price", -10000, "quantity", 1000, "orderType", "LO").
		expect().
	    statusCode(200).
	    body(equalTo("Price Lower Than Zezo")).
	    when().
	    post("/rest/order/place");
	}
	
	@Test
	public void placeOrderTestFailWithQuantityLowerThanZezo () {
		given().
		contentType(ContentType.JSON).
		formParams("id", "123", "account", "123456", "symbol", "VND", "price", 10000, "quantity", -1000, "orderType", "LO").
		expect().
	    statusCode(200).
	    body(equalTo("Quantity Lower Than Zezo")).
	    when().
	    post("/rest/order/place");
	}
	
	@Test
	public void placeOrderTestFailWithWrongOrderType () {
		given().
		contentType(ContentType.JSON).
		formParams("id", "123", "account", "123456", "symbol", "VND", "price", 10000, "quantity", 1000, "orderType", "GI").
		expect().
	    statusCode(200).
	    body(equalTo("Wrong Order Type")).
	    when().
	    post("/rest/order/place");
	}
	
	@Test
	public void placeOrderTestFailWithInvalidPrice () {
		given().
		contentType(ContentType.JSON).
		formParams("id", "123", "account", "123456", "symbol", "VND", "price", 20000, "quantity", 1000, "orderType", "LO").
		expect().
	    statusCode(200).
	    body(equalTo("Invalid Price")).
	    when().
	    post("/rest/order/place");
	}
	
	@Test
	public void getTopOrderTest () {
		given().
		contentType(ContentType.JSON).
		formParams("id", "1", "account", "11", "symbol", "VND", "price", 10000, "quantity", 1000, "orderType", "LO").
		expect().
	    statusCode(200).
	    when().
	    post("/rest/order/place");
		
		given().
		contentType(ContentType.JSON).
		formParams("id", "2", "account", "22", "symbol", "VND", "price", 10000, "quantity", 1000, "orderType", "LO").
		expect().
	    statusCode(200).
	    when().
	    post("/rest/order/place");
		
		given().
		contentType(ContentType.JSON).
		formParams("id", "3", "account", "33", "symbol", "VND", "price", 10000, "quantity", 10000, "orderType", "LO").
		expect().
	    statusCode(200).
	    when().
	    post("/rest/order/place");
		
		given().
		contentType(ContentType.JSON).
		formParams("id", "4", "account", "44", "symbol", "VND", "price", 10000, "quantity", 1000, "orderType", "LO").
		expect().
	    statusCode(200).
	    when().
	    post("/rest/order/place");
		
		given().
		contentType(ContentType.JSON).
		formParams("id", "5", "account", "55", "symbol", "VND", "price", 10000, "quantity", 1000, "orderType", "LO").
		expect().
	    statusCode(200).
	    when().
	    post("/rest/order/place");
		

		Order[] orders =  given().
		contentType(ContentType.JSON).
		when().
        get("/rest/order/toporder").as(Order[].class);
		
		Assert.assertTrue(orders.length > 0);
		Assert.assertTrue(orders[0].getId().equals("3"));
	}
	
	@Test
	public void getTopAccountTest () {
		given().
		contentType(ContentType.JSON).
		formParams("id", "1", "account", "11", "symbol", "VND", "price", 10000, "quantity", 1000, "orderType", "LO").
		expect().
	    statusCode(200).
	    when().
	    post("/rest/order/place");
		
		given().
		contentType(ContentType.JSON).
		formParams("id", "2", "account", "22", "symbol", "VND", "price", 10000, "quantity", 1000, "orderType", "LO").
		expect().
	    statusCode(200).
	    when().
	    post("/rest/order/place");
		
		given().
		contentType(ContentType.JSON).
		formParams("id", "3", "account", "33", "symbol", "VND", "price", 10000, "quantity", 10000, "orderType", "LO").
		expect().
	    statusCode(200).
	    when().
	    post("/rest/order/place");
		
		given().
		contentType(ContentType.JSON).
		formParams("id", "4", "account", "44", "symbol", "VND", "price", 10000, "quantity", 1000, "orderType", "LO").
		expect().
	    statusCode(200).
	    when().
	    post("/rest/order/place");
		
		given().
		contentType(ContentType.JSON).
		formParams("id", "5", "account", "55", "symbol", "VND", "price", 10000, "quantity", 1000, "orderType", "LO").
		expect().
	    statusCode(200).
	    when().
	    post("/rest/order/place");

		String[] account =  given().
		contentType(ContentType.JSON).
		when().
        get("/rest/order/topaccount").as(String[].class);
		
		Assert.assertTrue(account.length > 0);
		Assert.assertTrue(account[0].equals("33"));
	}
}
