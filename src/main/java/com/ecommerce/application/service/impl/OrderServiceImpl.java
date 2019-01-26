package com.ecommerce.application.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.application.dto.AddOrderDTO;
import com.ecommerce.application.dto.ModelIdDTO;
import com.ecommerce.application.dto.OrderResponseDTO;
import com.ecommerce.application.dto.UpdateOrderDTO;
import com.ecommerce.application.entity.Order;
import com.ecommerce.application.entity.Product;
import com.ecommerce.application.exception.InvalidEmailException;
import com.ecommerce.application.exception.InvalidQuantityException;
import com.ecommerce.application.exception.OrderNotFoundException;
import com.ecommerce.application.exception.OutofStockException;
import com.ecommerce.application.exception.ProductNotFoundException;
import com.ecommerce.application.exception.ResourceNotFoundException;
import com.ecommerce.application.repository.OrderRepository;
import com.ecommerce.application.repository.ProductRepository;
import com.ecommerce.application.service.OrderService;
import com.ecommerce.application.utils.ValidationUtils;

@Component
public class OrderServiceImpl implements OrderService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Override
	public void createOrder(AddOrderDTO addOrderDTO) throws ProductNotFoundException, InvalidQuantityException, OutofStockException, InvalidEmailException {
		
		log.info("OrderServiceImpl : createOrder() - entering");
		Order newOrder = new Order();
		
		Product product = productRepository.findByName(addOrderDTO.getProductName());
		if(product == null) {
			throw new ProductNotFoundException();
		}
		newOrder.setProductId(product);
		if(addOrderDTO.getOrderQuantity()<0 || addOrderDTO.getOrderQuantity() == 0) {
			throw new InvalidQuantityException();
		}
		else if(addOrderDTO.getOrderQuantity() > product.getAvailableQuantity()){
			throw new OutofStockException();
		}
		else {
			newOrder.setOrderQuantity(addOrderDTO.getOrderQuantity());
		}
		if(!ValidationUtils.isValidEmail(addOrderDTO.getEmailid())) {
			throw new InvalidEmailException();
		}
		newOrder.setEmailId(addOrderDTO.getEmailid());
		newOrder.setDateCreated(new Date());
		
		//update available quantity in product table
		product.setAvailableQuantity(product.getAvailableQuantity() - newOrder.getOrderQuantity());
		newOrder.setProductId(product);
		
		log.info("OrderServiceImpl : createOrder() - leaving");
		orderRepository.save(newOrder);
	}

	@Override
	public void deleteOrder(ModelIdDTO deleteOrderRequest) throws OrderNotFoundException {
		
		log.info("OrderServiceImpl : deleteOrder() - entering");
		if(!orderRepository.exists(deleteOrderRequest.getId())) {
			throw new OrderNotFoundException();
		} else {
			log.info("OrderServiceImpl : deleteOrder() - leaving");
			orderRepository.delete(deleteOrderRequest.getId());
		}
		
	}

	@Override
	public OrderResponseDTO viewDetails(ModelIdDTO viewDetailsRequest) throws OrderNotFoundException, ResourceNotFoundException {
		
		log.info("OrderServiceImpl : viewDetails() - entering");
		if(!orderRepository.exists(viewDetailsRequest.getId())) {
			throw new OrderNotFoundException();
		} 
			OrderResponseDTO orderResponse = new OrderResponseDTO();
			Order orderEntity = orderRepository.findOne(viewDetailsRequest.getId());
			if(orderEntity == null) {
				throw new ResourceNotFoundException();
			}
			orderResponse.setId(orderEntity.getId());
			if(orderEntity.getProductId() == null) {
				throw new ResourceNotFoundException();
			}
			orderResponse.setProductId(orderEntity.getProductId().getId());
			orderResponse.setProductName(orderEntity.getProductId().getName());
			orderResponse.setEmailid(orderEntity.getEmailId());
			orderResponse.setDateCreated(orderEntity.getDateCreated());
			orderResponse.setOrderQuantity(orderEntity.getOrderQuantity());
			
			log.info("OrderServiceImpl : viewDetails() - leaving");
			return orderResponse;
	}

	@Override
	public OrderResponseDTO updateOrder(UpdateOrderDTO updateRequest) throws ProductNotFoundException, InvalidQuantityException, OutofStockException, InvalidEmailException {
		
		log.info("OrderServiceImpl : updateOrder() - entering");
		
		OrderResponseDTO orderResponse = new OrderResponseDTO();
		
		Order updateOrder = orderRepository.findOne(updateRequest.getId());
		
		if(!ValidationUtils.isValidEmail(updateRequest.getEmailid())) {
			throw new InvalidEmailException();
		}
		updateOrder.setEmailId(updateRequest.getEmailid());
		
		Long currentQuantity = updateOrder.getOrderQuantity();
		if(updateRequest.getOrderQuantity()<0 || updateRequest.getOrderQuantity() == 0) {
			throw new InvalidQuantityException();
		}
		Long newQuantity = updateRequest.getOrderQuantity();
		Long effectiveAvailableQuantity = calculateEffective(currentQuantity, newQuantity, updateOrder.getProductId().getId());

		if(effectiveAvailableQuantity<0){
			throw new OutofStockException();
		}
		else {
			updateOrder.setOrderQuantity(newQuantity);
		}
	
		//update available quantity in product table	
		Product product = productRepository.findOne(updateOrder.getProductId().getId());
		product.setAvailableQuantity(effectiveAvailableQuantity);
		
		updateOrder.setProductId(product);
		
		orderRepository.save(updateOrder);
		
		orderResponse.setId(updateOrder.getId());
		/////////validation
		orderResponse.setProductId(updateOrder.getProductId().getId());
		orderResponse.setProductName(updateOrder.getProductId().getName());
		orderResponse.setOrderQuantity(updateOrder.getOrderQuantity());
		orderResponse.setEmailid(updateOrder.getEmailId());
		orderResponse.setDateCreated(updateOrder.getDateCreated());
		
		log.info("OrderServiceImpl : updateOrder() - leaving");
		return orderResponse;
	}

	private Long calculateEffective(Long currentQuantity, Long newQuantity, Long productId) {
		
		log.info("OrderServiceImpl : calculateEffective() - entering");
		Product product = productRepository.findOne(productId);
		Long effectiveAvailabeQuantity = null;
		if(currentQuantity > newQuantity) {
			effectiveAvailabeQuantity =  product.getAvailableQuantity() + (currentQuantity-newQuantity);
		}
		else if(currentQuantity < newQuantity){
			effectiveAvailabeQuantity = product.getAvailableQuantity() - (newQuantity - currentQuantity);
		}
		else {
			effectiveAvailabeQuantity = currentQuantity;
		}
		log.info("OrderServiceImpl : calculateEffective() - leaving");
		return effectiveAvailabeQuantity;
	}

	@Override
	public List<OrderResponseDTO> listAll() {
		
		log.info("OrderServiceImpl : listAll() - entering");

		List<OrderResponseDTO> orderResponseList = new ArrayList<OrderResponseDTO>();
		List<Order> orderEntityList = orderRepository.findAll();
		for(Order orderEntity : orderEntityList) {
			
			OrderResponseDTO orderResponse = new OrderResponseDTO();
			
			orderResponse.setId(orderEntity.getId());
			orderResponse.setProductId(orderEntity.getProductId().getId());
			orderResponse.setProductName(orderEntity.getProductId().getName());
			orderResponse.setEmailid(orderEntity.getEmailId());
			orderResponse.setDateCreated(orderEntity.getDateCreated());
			orderResponse.setOrderQuantity(orderEntity.getOrderQuantity());
			
			orderResponseList.add(orderResponse);
		}
		
		log.info("OrderServiceImpl : listAll() - leaving");
		return orderResponseList;
	}
}
