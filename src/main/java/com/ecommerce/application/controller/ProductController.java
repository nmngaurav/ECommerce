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
import com.ecommerce.application.dto.AddProductDTO;
import com.ecommerce.application.dto.ModelIdDTO;
import com.ecommerce.application.dto.UpdateProductDTO;
import com.ecommerce.application.exception.InvalidNameException;
import com.ecommerce.application.exception.InvalidPriceException;
import com.ecommerce.application.exception.InvalidQuantityException;
import com.ecommerce.application.exception.ProductAlreadyExistsException;
import com.ecommerce.application.exception.ProductNotFoundException;
import com.ecommerce.application.service.ProductService;
import com.ecommerce.application.utils.ECommerceResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Component
@Api(tags = "Product-API")
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@ApiOperation(value = "Add a new product", notes = "Add a new product")
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ECommerceResponse addProduct(@RequestBody AddProductDTO addProductDTO) {
		try {
			productService.addProduct(addProductDTO);
			return new ECommerceResponse("Product added", AppConstants.Status.SUCCESS, AppConstants.Status.NULL) ;
		} catch (InvalidNameException e) {
			return new ECommerceResponse("Invalid product name", AppConstants.Status.ERROR, AppConstants.Status.NULL) ;
		} catch (ProductAlreadyExistsException e) {
			return new ECommerceResponse("Product already exists", AppConstants.Status.ERROR, AppConstants.Status.NULL) ;
		} catch (InvalidPriceException e) {
			return new ECommerceResponse("Price can not be negative or zero", AppConstants.Status.ERROR, AppConstants.Status.NULL) ;
		} catch (InvalidQuantityException e) {
			return new ECommerceResponse("Quantity can not be negative", AppConstants.Status.ERROR, AppConstants.Status.NULL) ;
		}
		
	}
	
	@ApiOperation(value = "Delete product", notes = "delete service")
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
		public ECommerceResponse deleteProduct(@RequestBody ModelIdDTO pIdRequest) {
		try {
			productService.delete(pIdRequest);
			return new ECommerceResponse("Product deleted", AppConstants.Status.SUCCESS,  pIdRequest.getId()) ;
		} catch (ProductNotFoundException e) {
			return new ECommerceResponse("Product not found", AppConstants.Status.ERROR, AppConstants.Status.NULL);
		}
		
	}
	   
	@ApiOperation(value = "Update product", notes = "Update existing product details")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
		public ECommerceResponse updateDetails(@RequestBody UpdateProductDTO updateProductRequest)  {
		
		try {
			return new ECommerceResponse("Product updated", "success", productService.updateDetails(updateProductRequest) ) ;
		} catch (ProductNotFoundException e) {
			return new ECommerceResponse("Product not found", AppConstants.Status.ERROR, AppConstants.Status.NULL) ;
		} catch (InvalidNameException e) {
			return new ECommerceResponse("Invalid product name", AppConstants.Status.ERROR, AppConstants.Status.NULL) ;
		} catch (InvalidPriceException e) {
			return new ECommerceResponse("Price can not be negative or zero", AppConstants.Status.ERROR, AppConstants.Status.NULL) ;
		} catch (InvalidQuantityException e) {
			return new ECommerceResponse("Quantity can not be negative", AppConstants.Status.ERROR, AppConstants.Status.NULL) ;
		}
	}
	
	@ApiOperation(value = "View product", notes = "View product details")
	@RequestMapping(value = "/viewDetails",method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
		public ECommerceResponse viewDetails(@RequestBody ModelIdDTO viewDetailRequest ) {
		   
		try {
			return new ECommerceResponse("Product details", AppConstants.Status.SUCCESS, productService.viewDetails(viewDetailRequest) ) ;
		} catch (ProductNotFoundException e) {
			return new ECommerceResponse("Product not found", AppConstants.Status.ERROR, AppConstants.Status.NULL) ;
		}
	}
	
	@ApiOperation(value = "List all products", notes = "List all products")
	@RequestMapping(value = "/listAll",method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
		public ECommerceResponse listAllProducts() {
		   
		return new ECommerceResponse("List all products", AppConstants.Status.SUCCESS, productService.listAll() ) ;
	}
	
}
