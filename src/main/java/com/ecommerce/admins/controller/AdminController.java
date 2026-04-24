package com.ecommerce.admins.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.admins.dto.CreateAdminRequest;
import com.ecommerce.admins.dto.LoginAdminRequest;
import com.ecommerce.admins.entity.AdminInfo;
import com.ecommerce.admins.service.AdminService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {

	private final AdminService adminService;

	@PostMapping("/signup")
	public ResponseEntity<Void> createAdmin(@RequestBody @Valid CreateAdminRequest request) {
		adminService.save(request);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}



}
