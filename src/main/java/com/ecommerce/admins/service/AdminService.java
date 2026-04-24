package com.ecommerce.admins.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.admins.dto.CreateAdminRequest;
import com.ecommerce.admins.dto.LoginAdminRequest;
import com.ecommerce.admins.entity.Admin;
import com.ecommerce.admins.entity.AdminInfo;
import com.ecommerce.admins.repository.AdminRepository;
import com.ecommerce.common.PasswordEncoder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void save(@Valid CreateAdminRequest request) {
		if (adminRepository.existsByEmail(request.getEmail())) {
			throw new IllegalArgumentException("이미 사용중인 메일입니다.");
		}
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		adminRepository.save(new Admin(request.getName(), request.getEmail(), encodedPassword, request.getPhoneNumber(),
			request.getAdminRole()));
	}


}
