package vn.com.vndirect.api.service;

import vn.com.vndirect.api.model.Order;
import vn.com.vndirect.api.model.ValidateResult;

public interface IValidatorService {
	public ValidateResult validate(Order order);
}
