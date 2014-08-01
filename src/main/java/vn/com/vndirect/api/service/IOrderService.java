package vn.com.vndirect.api.service;

import java.util.List;

import vn.com.vndirect.api.model.Order;
import vn.com.vndirect.api.model.ValidateResult;

public interface IOrderService {
	public ValidateResult placeOrder(Order order);
	public ValidateResult replaceOrder(Order order);
	public ValidateResult cancelOrder(Order order);
	public List<Order> getTopOrder();
	public List<String> getTopAccount();
}
