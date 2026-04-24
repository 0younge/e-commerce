package com.ecommerce.products.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.products.dto.ProductRequest;
import com.ecommerce.products.dto.ProductResponse;
import com.ecommerce.products.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {

		ProductResponse response = productService.save(request);

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(response);
	}
}
