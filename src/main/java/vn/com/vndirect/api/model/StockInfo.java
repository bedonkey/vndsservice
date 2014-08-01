package vn.com.vndirect.api.model;

public class StockInfo {
	private String symbol;
	private double ceilingPrice;
	private double floorPrice;
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public double getCeilingPrice() {
		return ceilingPrice;
	}
	public void setCeilingPrice(double ceilingPrice) {
		this.ceilingPrice = ceilingPrice;
	}
	public double getFloorPrice() {
		return floorPrice;
	}
	public void setFloorPrice(double floorPrice) {
		this.floorPrice = floorPrice;
	}
}
