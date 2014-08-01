package vn.com.vndirect.api.service;

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
public class ValidatorServiceTest {
	
	@Autowired
	ValidatorServiceImpl validatorService;
	
	@Before
	public void setUp(){
	}
	
	@Test
	public void validateOrderWithoutError () {
		Order order = new Order("123", "123456", "VND", 10000, 1000, "LO");
		ValidateResult validateResult = validatorService.validate(order);
		Assert.assertEquals(validateResult.isValid(), true);
		
	}
	
	@Test
	public void validateOrderWithEmptyAccount () {
		Order order = new Order("123", "", "VND", 10000, 1000, "LO");
		ValidateResult validateResult = validatorService.validate(order);
		Assert.assertEquals(validateResult.isValid(), false);
		Assert.assertEquals(validateResult.getMessage(), "Account Empty");
		
	}
	
	@Test
	public void validateOrderWithEmptySymbol () {
		Order order = new Order("123", "123456", "", 10000, 1000, "LO");
		ValidateResult validateResult = validatorService.validate(order);
		Assert.assertEquals(validateResult.isValid(), false);
		Assert.assertEquals(validateResult.getMessage(), "Symbol Empty");		
	}
	
	@Test
	public void validateOrderWithPriceLowerThanZero () {
		Order order = new Order("123", "123456", "VND", -10000, 1000, "LO");
		ValidateResult validateResult = validatorService.validate(order);
		Assert.assertEquals(validateResult.isValid(), false);
		Assert.assertEquals(validateResult.getMessage(), "Price Lower Than Zezo");		
	}
	
	@Test
	public void validateOrderWithQuantityLowerThanZero () {
		Order order = new Order("123", "123456", "VND", 10000, -1000, "LO");
		ValidateResult validateResult = validatorService.validate(order);
		Assert.assertEquals(validateResult.isValid(), false);
		Assert.assertEquals(validateResult.getMessage(), "Quantity Lower Than Zezo");		
	}
	
	@Test
	public void validateOrderWithInvalidOrderType () {
		Order order = new Order("123", "123456", "VND", 10000, 1000, "GI");
		ValidateResult validateResult = validatorService.validate(order);
		Assert.assertEquals(validateResult.isValid(), false);
		Assert.assertEquals(validateResult.getMessage(), "Wrong Order Type");		
	}
}
