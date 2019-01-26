package com.ecommerce.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.application.constants.AppConstants;
import com.ecommerce.application.dto.AddOrderDTO;
import com.ecommerce.application.dto.ModelIdDTO;
import com.ecommerce.application.dto.UpdateOrderDTO;
import com.ecommerce.application.exception.InvalidEmailException;
import com.ecommerce.application.exception.InvalidQuantityException;
import com.ecommerce.application.exception.OrderNotFoundException;
import com.ecommerce.application.exception.OutofStockException;
import com.ecommerce.application.exception.ProductNotFoundException;
import com.ecommerce.application.exception.ResourceNotFoundException;
import com.ecommerce.application.service.OrderService;
import com.ecommerce.application.utils.ECommerceResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Component
@Api(tags = "Order-API")
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@ApiOperation(value = "Create a new order", notes = "Create a new order")
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ECommerceResponse createOrder(@RequestBody AddOrderDTO addOrderDTO) {
	
			try {
				orderService.createOrder(addOrderDTO);
				return new ECommerceResponse("Order created", AppConstants.Status.SUCCESS, AppConstants.Status.NULL);
			} catch (ProductNotFoundException e) {
				return new ECommerceResponse("Product not found with given name", AppConstants.Status.ERROR, AppConstants.Status.NULL);
			} catch (InvalidQuantityException e) {
				return new ECommerceResponse("Order quantity can not be negative or zero", AppConstants.Status.ERROR, AppConstants.Status.NULL);
			} catch (OutofStockException e) {
				return new ECommerceResponse("Product out of stock for given order quantity", AppConstants.Status.ERROR, AppConstants.Status.NULL);
			} catch (InvalidEmailException e) {
				return new ECommerceResponse("Invalid email id", AppConstants.Status.ERROR, AppConstants.Status.NULL);
			}
	
	}
	
	@ApiOperation(value = "Delete order", notes = "delete order")
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
		public ECommerceResponse deleteOrder(@RequestBody ModelIdDTO deleteRequest) {
		
			try {
				orderService.deleteOrder(deleteRequest);
				return new ECommerceResponse("Product deleted", AppConstants.Status.SUCCESS,  deleteRequest.getId()) ;
			} catch (OrderNotFoundException e) {
				return new ECommerceResponse("Order not found", AppConstants.Status.ERROR, AppConstants.Status.NULL);
			}
	}
	
	@ApiOperation(value = "Update order", notes = "Update existing order details")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
		public ECommerceResponse updateDetails(@RequestBody UpdateOrderDTO updateOrderRequest)  {
		
		try {
			return new ECommerceResponse("Order updated", AppConstants.Status.SUCCESS, orderService.updateOrder(updateOrderRequest)) ;
		} catch (ProductNotFoundException e) {
			return new ECommerceResponse("Product not found with given name", AppConstants.Status.ERROR, AppConstants.Status.NULL);
		} catch (InvalidQuantityException e) {
			return new ECommerceResponse("Order quantity can not be negative or zero", AppConstants.Status.ERROR, AppConstants.Status.NULL);
		} catch (OutofStockException e) {
			return new ECommerceResponse("Product out of stock for given order quantity", AppConstants.Status.ERROR, AppConstants.Status.NULL);
		} catch (InvalidEmailException e) {
			return new ECommerceResponse("Invalid email id", AppConstants.Status.ERROR, AppConstants.Status.NULL);
		}
	}
	
	@ApiOperation(value = "View order", notes = "View order details")
	@RequestMapping(value = "/viewDetails",method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
		public ECommerceResponse viewDetails(@RequestBody ModelIdDTO viewDetailRequest ) {
		   
		try {
			return new ECommerceResponse("Order details", AppConstants.Status.SUCCESS, orderService.viewDetails(viewDetailRequest));
		} catch (OrderNotFoundException e) {
			return new ECommerceResponse("Order not found", AppConstants.Status.ERROR, AppConstants.Status.NULL) ;
		}catch (ResourceNotFoundException e) {
			return new ECommerceResponse("Resource(s) not found", AppConstants.Status.ERROR, AppConstants.Status.NULL) ;
		}
	}
	
	@ApiOperation(value = "List all orders", notes = "List all orders")
	@RequestMapping(value = "/listAll",method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
		public ECommerceResponse listAllOrders() {
		   
		return new ECommerceResponse("List all orders", AppConstants.Status.SUCCESS, orderService.listAll() ) ;
	}
}
