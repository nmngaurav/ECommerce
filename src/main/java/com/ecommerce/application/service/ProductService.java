package com.ecommerce.application.service;

import java.util.List;

import com.ecommerce.application.dto.AddProductDTO;
import com.ecommerce.application.dto.ModelIdDTO;
import com.ecommerce.application.dto.UpdateProductDTO;
import com.ecommerce.application.entity.Product;
import com.ecommerce.application.exception.InvalidNameException;
import com.ecommerce.application.exception.InvalidPriceException;
import com.ecommerce.application.exception.InvalidQuantityException;
import com.ecommerce.application.exception.ProductAlreadyExistsException;
import com.ecommerce.application.exception.ProductNotFoundException;

public interface ProductService {

	public void addProduct(AddProductDTO addProductDTO) throws ProductAlreadyExistsException, InvalidNameException, InvalidPriceException, InvalidQuantityException;

	public void delete(ModelIdDTO productDeleteRequest) throws ProductNotFoundException;
	
	public Product updateDetails(UpdateProductDTO updateProductRequest) throws ProductNotFoundException, InvalidNameException, InvalidPriceException, InvalidQuantityException;
	
	public Product viewDetails(ModelIdDTO viewDetailRequest) throws ProductNotFoundException;
	
	public List<Product> listAll();
	
}