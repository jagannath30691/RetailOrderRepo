package com.assignment.retailorder;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.assignment.retailorder.constants.OrderConstants;
import com.assignment.retailorder.entity.RetailOrder;

@RunWith(SpringJUnit4ClassRunner.class)
class RetailorderApplicationTests extends AbstractRetailorderApplicationTests{

	
	@Override
	   @Before
	   public void setUp() {
	      super.setUp();
	   }
	  
	   @Test
	   public void createProduct() throws Exception {
	      String uri = "/api/v1/retail/placeorder";
	      RetailOrder order = new RetailOrder();
	      order.setCustomerName("John Clusener");
	      order.setOrderId(1002l);
	      order.setOrderIdentifier("ORDER1002");
	      order.setStatus(OrderConstants.PLACED);
	      
	      String inputJson = super.mapToJson(order);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(201, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      assertEquals(content, "Order is created successfully");
	   }
	   
	   
	   @Test
	   public void updateProduct() throws Exception {
	      String uri = "/api/v1/retail/updateorder/1002";
	      RetailOrder order = new RetailOrder();
	      order.setOrderId(1002l);
	      order.setStatus(OrderConstants.PROCESSED);
	      String inputJson = super.mapToJson(order);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      assertEquals(content, "Order is updated successsfully");
	   }
	   
	   @Test
	   public void getProductsList() throws Exception {
	      String uri = "/api/v1/retail/fetchOrder/1002";
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      RetailOrder order = super.mapFromJson(content, RetailOrder.class);
	      assertTrue(order != null);
	   }
}
