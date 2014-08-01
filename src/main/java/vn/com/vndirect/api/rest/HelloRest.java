package vn.com.vndirect.api.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.com.vndirect.api.service.MasterSlave;

@Component
@Path("/hello")
public class HelloRest {
	@Autowired
	MasterSlave masterSlave;
	
	@GET
	@Path("/masterorslave")
	public Response sayHello() {
		String output = "";
		if (masterSlave.isMaster()) {
			output = "Hello Master";
		} else {
			output = "Hello Slave";
		}
		return Response.status(200).entity(output).build();
	}
 
}