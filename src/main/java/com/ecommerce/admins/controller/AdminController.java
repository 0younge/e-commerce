package com.ecommerce.admins.controller;

import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.admins.dto.CreateAdminRequest;
import com.ecommerce.admins.dto.GetAdminResponse;
import com.ecommerce.admins.dto.GetOneAdminResponse;
import com.ecommerce.admins.dto.LoginAdminRequest;
import com.ecommerce.admins.dto.UpdateAdminRequest;
import com.ecommerce.admins.entity.AdminConst;
import com.ecommerce.admins.entity.AdminInfo;
import com.ecommerce.admins.entity.AdminRole;
import com.ecommerce.admins.service.AdminService;
import com.ecommerce.common.enums.AdminStatus;

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

	@PostMapping("/login")
	public ResponseEntity<Void> loginAdmin(@RequestBody @Valid LoginAdminRequest request, HttpSession session) {
		AdminInfo adminInfo = adminService.login(request, checkSessionOrThrow(session));
		session.setMaxInactiveInterval(86400); // 세션만료: 24시간
		session.setAttribute(AdminConst.ADMIN_INFO, adminInfo);

		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<Page<GetAdminResponse>> getAdminList(@RequestParam(required = false) String keyword,
		@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "createdAt") String sortBy, @RequestParam(defaultValue = "desc") String sortOrder,
		@RequestParam(required = false) AdminRole role, @RequestParam(required = false) AdminStatus status,
		HttpSession session) {
		Pageable pageable = PageRequest.of(page - 1, size,
			sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

		return ResponseEntity.ok(
			adminService.getAdminList(keyword, role, status, pageable, checkSessionOrThrow(session)));
	}

	@GetMapping("/{adminId}")
	public ResponseEntity<GetOneAdminResponse> getOneAdmin(@PathVariable Long adminId, HttpSession session) {
		return ResponseEntity.ok(adminService.getOne(adminId, checkSessionOrThrow(session)));
	}

	@PatchMapping("/{adminId}")
	public ResponseEntity<Void> updateAdmin(@RequestBody UpdateAdminRequest request, @PathVariable Long adminId,
		HttpSession session) {
		adminService.update(adminId, request, checkSessionOrThrow(session));

		return ResponseEntity.ok().build();
	}

	public AdminInfo checkSessionOrThrow(HttpSession session) {
		AdminInfo adminInfo = (AdminInfo)session.getAttribute(AdminConst.ADMIN_INFO);
		if (adminInfo == null) {
			throw new IllegalStateException("로그인이 필요한 작업입니다.");
		}
		return adminInfo;
	}

}
