package vn.com.vndirect.api.service;

import java.util.Arrays;
import java.util.List;

import vn.com.vndirect.api.model.Order;
import vn.com.vndirect.api.model.ValidateResult;

public class ValidatorServiceImpl implements IValidatorService{
	
	private String orderType;
	
	private List<String> orderTypeList;
	
	public void init() {
		orderTypeList = Arrays.asList(orderType.split(","));
	}
	
	@Override
	public ValidateResult validate(Order order) {
		ValidateResult validateResult = new ValidateResult();
		if (order.getAccount() == "") {
			validateResult.setValid(false);
			validateResult.setMessage("Account Empty");
			return validateResult;
		}
		if (order.getSymbol() == "") {
			validateResult.setValid(false);
			validateResult.setMessage("Symbol Empty");
			return validateResult;
		}
		if (order.getPrice() < 0) {
			validateResult.setValid(false);
			validateResult.setMessage("Price Lower Than Zezo");
			return validateResult;
		} 
		if (order.getQuantity() < 0) {
			validateResult.setValid(false);
			validateResult.setMessage("Quantity Lower Than Zezo");
			return validateResult;
		} 
		if (!orderTypeList.contains(order.getOrderType())) {
			validateResult.setValid(false);
			validateResult.setMessage("Wrong Order Type");
			return validateResult;
		}
		
		validateResult.setValid(true);
		return validateResult;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

}
