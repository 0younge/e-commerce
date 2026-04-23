package com.ecommerce.admins.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.admins.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

}
