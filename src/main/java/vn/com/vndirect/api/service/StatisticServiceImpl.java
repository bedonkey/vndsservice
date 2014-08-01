package vn.com.vndirect.api.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import vn.com.vndirect.api.model.Order;
import vn.com.vndirect.api.utils.ValueComparator;

public class StatisticServiceImpl implements IStaticticService{
	List<Order> listOrders;
	private int numberReport;
	
	public StatisticServiceImpl() {
		listOrders = new ArrayList<Order>();
	}
	
	@Override
	public void addOrder(Order order){
		listOrders.add(order);
	}
	
	@Override
	public void resetOrders() {
		 listOrders = new ArrayList<Order>();
	}
	
	@Override
	public List<Order> getTopOrder() {
		List<Order> list = sortListOrder(listOrders);
		int listSize = list.size();
		int top = listSize > numberReport ? numberReport : listSize - 1;
		return list.subList(0, top);
	}
	
	private List<Order> sortListOrder(List<Order> list) {
		Collections.sort(list, new Comparator<Order>() {
	        @Override
	        public int compare(Order  order1, Order  order2)
	        {
	        	Double orderValue1 = order1.getPrice() * order1.getQuantity();
	        	Double orderValue2 = order2.getPrice() * order2.getQuantity();
	            return  orderValue2.compareTo(orderValue1);
	        }
	    });
		return list;
	}
	
	@Override
	public List<String> getTopAccount() {
		Map<String, Double> mapTopAccount = new HashMap<String, Double>();
		for (final Order order : listOrders) {
			if (mapTopAccount.containsKey(order.getAccount())) {
				mapTopAccount.put(order.getAccount(),mapTopAccount.get(order.getAccount()) + order.getPrice() * order.getQuantity());
			} else {
				mapTopAccount.put(order.getAccount(), order.getPrice() * order.getQuantity());
			}
		}
        List<String> listResurn = new ArrayList<String>(sortMap(mapTopAccount).keySet());
        int top = listResurn.size() > numberReport ? numberReport : listResurn.size() - 1;
		return listResurn.subList(0, top);
	}
	
	private TreeMap<String,Double> sortMap(Map<String, Double> mapTopAccount) {
		ValueComparator bvc =  new ValueComparator(mapTopAccount);
        TreeMap<String,Double> sorted_map = new TreeMap<String,Double>(bvc);
        sorted_map.putAll(mapTopAccount);
        return sorted_map;
	}

	public void setNumberReport(int numberReport) {
		this.numberReport = numberReport;
	}
}
