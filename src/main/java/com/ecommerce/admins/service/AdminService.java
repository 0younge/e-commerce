package com.ecommerce.admins.service;

import org.springframework.stereotype.Service;

import com.ecommerce.admins.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final AdminRepository adminRepository;

}
