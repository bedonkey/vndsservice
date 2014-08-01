package vn.com.vndirect.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import vn.com.vndirect.api.model.Order;
import vn.com.vndirect.api.model.StockInfo;
import vn.com.vndirect.api.model.ValidateResult;
import vn.com.vndirect.stock.StockInfoService;

public class OrderServiceImpl implements IOrderService{
	@Autowired
	SpringAMQP springAMQP;
	
	private ValidatorServiceImpl validatorService;
	private StockInfoService stockInfoService;
	private StatisticServiceImpl statisticService;
	
	public OrderServiceImpl(ValidatorServiceImpl validatorService, StockInfoService stockInfoService, StatisticServiceImpl statisticService) {
		this.validatorService = validatorService;
		this.stockInfoService = stockInfoService;
		this.statisticService = statisticService;
	}
	
	@Override
	public ValidateResult placeOrder(Order order) {
		ValidateResult validateResult = validateOrder(order);
		if (validateResult.isValid()) {
			// Send order to backend
			springAMQP.sendOrderToQueue(order);
			validateResult.setMessage(order.getId());
		}
		return validateResult;
	}
	
	@Override
	public ValidateResult replaceOrder(Order order) {
		ValidateResult validateResult = validateOrder(order);
		if (validateResult.isValid()) {
			// Send order to backend
		}
		return validateResult;
	}

	@Override
	public ValidateResult cancelOrder(Order order) {
		ValidateResult validateResult = validateOrder(order);
		if (validateResult.isValid()) {
			// Send order to backend
		}
		return validateResult;
	}
	
	private ValidateResult validateOrder (Order order) {
		ValidateResult validateResult = validatorService.validate(order);
		if (!validateResult.isValid()) {
			return validateResult;
		}
		if (!isPriceValid(order)) {
			validateResult.setValid(false);
			validateResult.setMessage("Invalid Price");
		}
		return validateResult;
	}
	
	private boolean isPriceValid(Order order) {
		StockInfo stockInfo = stockInfoService.getPrice(order.getSymbol());
		if (stockInfo.getFloorPrice() <= order.getPrice() && order.getPrice() <= stockInfo.getCeilingPrice()) {
			return true;
		}
		return false;
	}
	
	@Override
	public List<Order> getTopOrder() {
		return statisticService.getTopOrder();
	}
	
	@Override
	public List<String> getTopAccount() {
		return statisticService.getTopAccount();
	}
}
