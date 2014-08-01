package vn.com.vndirect.api.service;

import org.zk.Application;
import org.zk.ApplicationListener;
import org.zk.ApplicationState;

public class MasterSlave implements ApplicationListener{
	private ApplicationState state;
	private Application app; 
	private String zooKeeperAddr;
	private String masterNode;
	private StatisticServiceImpl statisticService;
	
	public MasterSlave(StatisticServiceImpl statisticService) {
		this.statisticService = statisticService;
	}
	
	public void init() {
		app = new Application(zooKeeperAddr, masterNode);
		app.registListener(this);
		app.start();
	}
	
	public void stop() {
		app.stop();
	}
	
	@Override
	public void onChange(ApplicationState state) {
		this.state = state;
		if (state == ApplicationState.SLAVE) {
			statisticService.resetOrders();
		}
	}
	
	public boolean isMaster() {
		if (state == ApplicationState.MASTER) {
			return true;
		} else {
			return false;
		}
	}

	public void setZooKeeperAddr(String zooKeeperAddr) {
		this.zooKeeperAddr = zooKeeperAddr;
	}

	public void setMasterNode(String masterNode) {
		this.masterNode = masterNode;
	}
}
