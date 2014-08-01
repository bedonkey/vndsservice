package vn.com.vndirect.api.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.com.vndirect.api.model.Order;
import vn.com.vndirect.api.model.ValidateResult;
import vn.com.vndirect.api.service.IOrderService;
import vn.com.vndirect.api.service.MasterSlave;

@Component
@Path("/order")
public class OrderRest{
	
	@Autowired
	IOrderService orderService;
	
	@Autowired
	MasterSlave masterSlave;
	
	public OrderRest() {
	}
	
	@POST
	@Path("/place")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response placeOrder(Order order) throws Exception{
		
		if (!masterSlave.isMaster()) {
			return Response.status(404).entity("").build();
		}
		ValidateResult validateResult = orderService.placeOrder(order);
		return Response.status(200).entity(validateResult.getMessage()).build();
	}
	
	@GET
	@Path("/toporder")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Order> selectTopOrder() throws Exception{
		if (!masterSlave.isMaster()) {
			return null;
		}
		List<Order> listOrder = orderService.getTopOrder();
		return listOrder;
	}
	
	@GET
	@Path("/topaccount")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> selectTopAccount() throws Exception{
		if (!masterSlave.isMaster()) {
			return null;
		}
		List<String> listAccount = orderService.getTopAccount();
		return listAccount;
	}
	
}