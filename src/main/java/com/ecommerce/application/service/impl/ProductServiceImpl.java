package com.ecommerce.application.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.application.constants.AppConstants;
import com.ecommerce.application.dto.AddProductDTO;
import com.ecommerce.application.dto.ModelIdDTO;
import com.ecommerce.application.dto.UpdateProductDTO;
import com.ecommerce.application.entity.Product;
import com.ecommerce.application.exception.InvalidNameException;
import com.ecommerce.application.exception.InvalidPriceException;
import com.ecommerce.application.exception.InvalidQuantityException;
import com.ecommerce.application.exception.ProductAlreadyExistsException;
import com.ecommerce.application.exception.ProductNotFoundException;
import com.ecommerce.application.repository.ProductRepository;
import com.ecommerce.application.service.ProductService;
import com.ecommerce.application.utils.ValidationUtils;

@Component
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	
	private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Override
	public void addProduct(AddProductDTO addProductDTO) throws InvalidNameException, ProductAlreadyExistsException, InvalidPriceException, InvalidQuantityException {
		
		log.info("ProductServiceImpl : addProduct() - entering");
		Product newProduct = new Product();
		if(!ValidationUtils.isValidName(addProductDTO.getName())) {
			throw new InvalidNameException();
		}
		else if(productRepository.findByName(addProductDTO.getName()) !=null) {
			throw new ProductAlreadyExistsException();
		}
		
		else {
			newProduct.setName(addProductDTO.getName());
		}
		newProduct.setDescription(addProductDTO.getDescription()); 
		if(addProductDTO.getPrice()<0 || addProductDTO.getPrice() == 0) {
			throw new InvalidPriceException();
		}
		newProduct.setPrice(addProductDTO.getPrice());	
		if(addProductDTO.getAvailableQuantity()<0) {
			throw new InvalidQuantityException();
		}
		newProduct.setAvailableQuantity(addProductDTO.getAvailableQuantity());
		if(addProductDTO.getImageUrl().isEmpty()) {
			newProduct.setImageUrl(AppConstants.Status.DEFAULT_AVATAR_URL);
		}else {
			newProduct.setImageUrl(addProductDTO.getImageUrl());
		}
		log.info("ProductServiceImpl : addProduct() - leaving");
		productRepository.save(newProduct);
	}

	@Override
	public void delete(ModelIdDTO productDeleteRequest) throws ProductNotFoundException {
		log.info("ProductServiceImpl : delete() - entering");
		if(!productRepository.exists(productDeleteRequest.getId())) {
			throw new ProductNotFoundException();
		} else {
			log.info("ProductServiceImpl : delete() - leaving");
			productRepository.delete(productDeleteRequest.getId());
		}
	}

	@Override
	public Product updateDetails(UpdateProductDTO updateProductRequest) throws ProductNotFoundException, InvalidNameException, InvalidPriceException, InvalidQuantityException {

		log.info("ProductServiceImpl : updateDetails() - entering");
		if(!productRepository.exists(updateProductRequest.getId())) {
			throw new ProductNotFoundException();
		} 
		
		Product product = productRepository.findOne(updateProductRequest.getId());
			
		if(!ValidationUtils.isValidName(updateProductRequest.getName())) {
			throw new InvalidNameException();
		}
		product.setName(updateProductRequest.getName());
		product.setDescription(updateProductRequest.getDescription());
		if(updateProductRequest.getPrice()<0) {
			throw new InvalidPriceException();
		}
		product.setPrice(updateProductRequest.getPrice());
		if(updateProductRequest.getAvailableQuantity()<0) {
			throw new InvalidQuantityException();
		}
		product.setAvailableQuantity(updateProductRequest.getAvailableQuantity());		
		if(updateProductRequest.getImageUrl().isEmpty()) {
			product.setImageUrl(AppConstants.Status.DEFAULT_AVATAR_URL);
		}
		product.setImageUrl(updateProductRequest.getImageUrl());
		productRepository.save(product);
		log.info("ProductServiceImpl : updateDetails() - leaving");
		return product;
	}

	@Override
	public Product viewDetails(ModelIdDTO viewDetailRequest) throws ProductNotFoundException {
		
		log.info("ProductServiceImpl : viewDetails() - entering");
		
		if(!productRepository.exists(viewDetailRequest.getId())) {
			throw new ProductNotFoundException();
		} 
		log.info("ProductServiceImpl : viewDetails() - leaving");
			return productRepository.findOne(viewDetailRequest.getId());
	}

	@Override
	public List<Product> listAll() {
		
		log.info("ProductServiceImpl : listAll() - entering");
		log.info("ProductServiceImpl : listAll() - leaving");
		return productRepository.findAll();
	}

}
