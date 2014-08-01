package vn.com.vndirect.api.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vn.com.vndirect.api.model.Order;
import vn.com.vndirect.api.model.ValidateResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-module.xml" })
public class OrderServiceTest {
	@Autowired
	OrderServiceImpl orderService;
	
	@Before
	public void setUp(){
	}
	
	@Test
	public void placeOrderWithoutError () {
		Order order = new Order("123", "123456", "VND", 10000, 1000, "LO");
		ValidateResult validateResult = orderService.placeOrder(order);
		Assert.assertEquals(validateResult.isValid(), true);
		Assert.assertEquals(validateResult.getMessage(), "123");
		
	}
	
	@Test
	public void placeOrderWithEmptyAccount () {
		Order order = new Order("123", "", "VND", 10000, 1000, "LO");
		ValidateResult validateResult = orderService.placeOrder(order);
		Assert.assertEquals(validateResult.isValid(), false);
		Assert.assertEquals(validateResult.getMessage(), "Account Empty");
		
	}
	
	@Test
	public void placeOrderWithEmptySymbol () {
		Order order = new Order("123", "123456", "", 10000, 1000, "LO");
		ValidateResult validateResult = orderService.placeOrder(order);
		Assert.assertEquals(validateResult.isValid(), false);
		Assert.assertEquals(validateResult.getMessage(), "Symbol Empty");
		
	}
	
	@Test
	public void placeOrderWithPriceLowerThanZero () {
		Order order = new Order("123", "123456", "VND", -10000, 1000, "LO");
		ValidateResult validateResult = orderService.placeOrder(order);
		Assert.assertEquals(validateResult.isValid(), false);
		Assert.assertEquals(validateResult.getMessage(), "Price Lower Than Zezo");
		
	}
	
	@Test
	public void placeOrderWithQuantityLowerThanZero () {
		Order order = new Order("123", "123456", "VND", 10000, -1000, "LO");
		ValidateResult validateResult = orderService.placeOrder(order);
		Assert.assertEquals(validateResult.isValid(), false);
		Assert.assertEquals(validateResult.getMessage(), "Quantity Lower Than Zezo");
		
	}
	
	@Test
	public void placeOrderWithInvalidOrderType () {
		Order order = new Order("123", "123456", "VND", 10000, 1000, "GI");
		ValidateResult validateResult = orderService.placeOrder(order);
		Assert.assertEquals(validateResult.isValid(), false);
		Assert.assertEquals(validateResult.getMessage(), "Wrong Order Type");
		
	}
	
	@Test
	public void placeOrderWithInvalidPrice () {
		Order order = new Order("123", "123456", "VND", 20000, 1000, "LO");
		ValidateResult validateResult = orderService.placeOrder(order);
		Assert.assertEquals(validateResult.isValid(), false);
		Assert.assertEquals(validateResult.getMessage(), "Invalid Price");
		
	}
	
	@Test
	public void getTopOrderTest () {
		Order order = new Order("1", "123456", "VND", 10000, 1000, "LO");
		orderService.placeOrder(order);
		
		order = new Order("2", "123456", "VND", 10000, 1000, "LO");
		orderService.placeOrder(order);
		
		order = new Order("3", "123456", "VND", 10000, 1000, "LO");
		orderService.placeOrder(order);
		
		order = new Order("4", "123456", "VND", 10000, 1000, "LO");
		orderService.placeOrder(order);
		
		order = new Order("5", "123456", "VND", 10000, 1000, "LO");
		orderService.placeOrder(order);
		
		order = new Order("6", "123456", "VND", 10000, 10000, "LO");
		orderService.placeOrder(order);
		
		order = new Order("7", "123456", "VND", 10000, 1000, "LO");
		orderService.placeOrder(order);
		
		order = new Order("8", "123456", "VND", 10000, 1000, "LO");
		orderService.placeOrder(order);
		
		List<Order> topOrders = orderService.getTopOrder();
		
		Assert.assertTrue(topOrders.size() > 0);
		Assert.assertTrue(topOrders.get(0).getId().equals("6"));
	}
	
	@Test
	public void getTopAccountTest () throws Exception{
		Order order = new Order("1", "123456", "VND", 10000, 1000, "LO");
		orderService.placeOrder(order);
		
		order = new Order("2", "123456", "VND", 10000, 1000, "LO");
		orderService.placeOrder(order);
		
		order = new Order("3", "123456", "VND", 10000, 1000, "LO");
		orderService.placeOrder(order);
		
		order = new Order("4", "123456", "VND", 10000, 1000, "LO");
		orderService.placeOrder(order);
		
		order = new Order("5", "123", "VND", 10000, 1000, "LO");
		orderService.placeOrder(order);
		
		order = new Order("6", "123", "VND", 10000, 10000, "LO");
		orderService.placeOrder(order);
		
		order = new Order("7", "123", "VND", 10000, 1000, "LO");
		orderService.placeOrder(order);
		
		order = new Order("8", "123", "VND", 10000, 1000, "LO");
		orderService.placeOrder(order);
		
		order = new Order("9", "12345678", "VND", 10000, 1000000, "LO");
		orderService.placeOrder(order);
		
		Thread.sleep(3000);
		
		List<String> topAccounts = orderService.getTopAccount();
		
		Assert.assertTrue(topAccounts.size() > 0);
		Assert.assertTrue(topAccounts.get(0).equals("12345678"));
		
	}
	
}
