package com.ecommerce.application.service;

import java.util.List;

import com.ecommerce.application.dto.AddOrderDTO;
import com.ecommerce.application.dto.ModelIdDTO;
import com.ecommerce.application.dto.OrderResponseDTO;
import com.ecommerce.application.dto.UpdateOrderDTO;
import com.ecommerce.application.exception.InvalidEmailException;
import com.ecommerce.application.exception.InvalidQuantityException;
import com.ecommerce.application.exception.OrderNotFoundException;
import com.ecommerce.application.exception.OutofStockException;
import com.ecommerce.application.exception.ProductNotFoundException;
import com.ecommerce.application.exception.ResourceNotFoundException;

public interface OrderService {

	public void createOrder(AddOrderDTO addOrderDTO) throws ProductNotFoundException, InvalidQuantityException, OutofStockException, InvalidEmailException;
	
	public void deleteOrder(ModelIdDTO deleteOrderRequest) throws OrderNotFoundException;
	
	public OrderResponseDTO viewDetails(ModelIdDTO viewDetailsRequest) throws OrderNotFoundException, ResourceNotFoundException;
	
	public OrderResponseDTO updateOrder(UpdateOrderDTO updateRequest) throws ProductNotFoundException, InvalidQuantityException, OutofStockException, InvalidEmailException;
	
	public List<OrderResponseDTO> listAll();
}
