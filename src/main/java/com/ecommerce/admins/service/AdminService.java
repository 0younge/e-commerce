package com.ecommerce.admins.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.admins.dto.CreateAdminRequest;
import com.ecommerce.admins.dto.GetAdminResponse;
import com.ecommerce.admins.dto.GetOneAdminResponse;
import com.ecommerce.admins.dto.LoginAdminRequest;
import com.ecommerce.admins.dto.UpdateAdminRequest;
import com.ecommerce.admins.entity.Admin;
import com.ecommerce.admins.entity.AdminInfo;
import com.ecommerce.admins.entity.AdminRole;
import com.ecommerce.admins.repository.AdminRepository;
import com.ecommerce.common.PasswordEncoder;
import com.ecommerce.common.enums.AdminStatus;

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

	@Transactional(readOnly = true)
	public AdminInfo login(@Valid LoginAdminRequest request, AdminInfo adminInfo) {
		Admin admin = findByIdOrThrow(adminInfo.getAdminId());
		if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
			throw new IllegalArgumentException("메일과 비밀번호가 일치하지 않습니다.");
		}
		switch (admin.getStatus()) {
			case INACTIVE -> throw new IllegalArgumentException("계정 비활성화됨");
			case SUSPENDED -> throw new IllegalArgumentException("계정 정지됨");
			case PENDING -> throw new IllegalArgumentException("계정 승인대기중");
			case REJECTED -> throw new IllegalArgumentException("계정 신청 거부됨");
		}

		return new AdminInfo(admin.getAdminId(), admin.getEmail(), admin.getRole());
	}

	@Transactional(readOnly = true)
	public Page<GetAdminResponse> getAdminList(String keyword, AdminRole role, AdminStatus status, Pageable pageable,
		AdminInfo adminInfo) {
		checkRoleOrThrow(adminInfo);

		return adminRepository.findAllByCondition(keyword, role, status, pageable).map(GetAdminResponse::from);
	}

	@Transactional(readOnly = true)
	public GetOneAdminResponse getOne(Long adminId, AdminInfo adminInfo) {
		checkRoleOrThrow(adminInfo);
		Admin admin = findByIdOrThrow(adminId);

		return GetOneAdminResponse.from(admin);
	}

	@Transactional
	public void update(Long adminId, UpdateAdminRequest request, AdminInfo adminInfo) {
		checkRoleOrThrow(adminInfo);
		Admin admin = findByIdOrThrow(adminId);

		admin.updateAdmin(request.getName(), request.getEmail(), request.getPhoneNumber());
	}

	public void checkRoleOrThrow(AdminInfo adminInfo) {
		if (!adminInfo.getRole().equals(AdminRole.SUPER_ADMIN)) {
			throw new IllegalArgumentException("권한이 없습니다");
		}
	}

	public Admin findByIdOrThrow(Long adminId) {
		return adminRepository.findById(adminId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
	}

}
