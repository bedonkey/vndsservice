/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */
package vn.com.vndirect.api.service;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.JndiRegistry;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import vn.com.vndirect.api.model.Order;
import amqp.spring.camel.component.SpringAMQPComponent;

public class SpringAMQP {

	@Autowired
	StatisticServiceImpl statisticService;
	
	protected volatile CamelContext context;
	protected volatile ProducerTemplate template;
	protected volatile ConsumerTemplate consumer;
	
	private String addressesAmqp;
	private String usernameAmqp;
	private String passwordAmqp;
	
	private static final String ORDER_EXCHANGE_QUEUE = "seda:orderQueue";
	
	public SpringAMQP() {
		super();
	}

	public void init() throws Exception {
		context = createCamelContext();
		addAmqpCamelContext();
		template = context.createProducerTemplate();
		template.start();
		consumer = context.createConsumerTemplate();
		consumer.start();
		context.addRoutes(createRouteBuilder());
		context.start();
	}

	public void stop() throws Exception {
		template.stop();
		consumer.stop();
		context.stop();
	}

	protected JndiRegistry createRegistry() throws Exception {
		return new JndiRegistry(createJndiContext());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Context createJndiContext() throws Exception {
		Properties properties = new Properties();

		// jndi.properties is optional
		InputStream in = getClass().getClassLoader().getResourceAsStream(
				"jndi.properties");
		if (in != null) {
			properties.load(in);
		} else {
			properties.put("java.naming.factory.initial",
					"org.apache.camel.util.jndi.CamelInitialContextFactory");
		}
		return new InitialContext(new Hashtable(properties));
	}

	protected CamelContext createCamelContext() throws Exception {
		CamelContext context = new DefaultCamelContext(createRegistry());
		context.setLazyLoadTypeConverters(true);
		return context;
	}

	protected CamelContext addAmqpCamelContext() throws Exception {
		CachingConnectionFactory factory = new CachingConnectionFactory();
    	factory.setAddresses(addressesAmqp);
		factory.setUsername(usernameAmqp);
		factory.setPassword(passwordAmqp);
		factory.setPort(5672);
		RabbitTemplate amqpTemplate = new RabbitTemplate(factory);
		amqpTemplate.setMessageConverter(new JsonMessageConverter());
		SpringAMQPComponent amqpComponent = new SpringAMQPComponent(factory);
		amqpComponent.setAmqpTemplate(amqpTemplate);
		context.addComponent("spring-amqp", amqpComponent);
		return context;
	}
	
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from(ORDER_EXCHANGE_QUEUE).process(new Processor() {
					@Override
					public void process(Exchange ex) throws Exception {
						Order order = (Order) ex.getIn().getBody();
						statisticService.addOrder(order);
					}
				});
			}
		};
	}

	public void sendOrderToQueue(Order order){
		template.sendBody(ORDER_EXCHANGE_QUEUE, order);
	}
	
	public void setAddressesAmqp(String addressesAmqp) {
		this.addressesAmqp = addressesAmqp;
	}

	public void setUsernameAmqp(String usernameAmqp) {
		this.usernameAmqp = usernameAmqp;
	}

	public void setPasswordAmqp(String passwordAmqp) {
		this.passwordAmqp = passwordAmqp;
	}
	
}
