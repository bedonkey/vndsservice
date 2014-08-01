package vn.com.vndirect.api.service;

import java.util.List;

import vn.com.vndirect.api.model.Order;

public interface IStaticticService {
	public List<Order> getTopOrder();
	public List<String> getTopAccount();
	public void addOrder(Order order);
	public void resetOrders();
}
