package com.ecommerce.products.service;

import org.springframework.stereotype.Service;

import com.ecommerce.products.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

}
