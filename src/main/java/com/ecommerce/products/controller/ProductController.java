package com.ecommerce.products.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.products.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

}
