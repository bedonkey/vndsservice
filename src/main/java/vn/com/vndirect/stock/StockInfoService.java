package vn.com.vndirect.stock;

import vn.com.vndirect.api.model.StockInfo;

public class StockInfoService {
	public StockInfo getPrice(String code) {
		StockInfo stockInfo = new StockInfo();
		stockInfo.setSymbol(code);
		stockInfo.setCeilingPrice(10000);
		stockInfo.setFloorPrice(1000);
		return stockInfo;
	}
}
