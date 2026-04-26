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
import com.ecommerce.admins.dto.UpdateRoleAdminRequest;
import com.ecommerce.admins.dto.UpdateStatusAdminRequest;
import com.ecommerce.admins.entity.Admin;
import com.ecommerce.admins.entity.AdminInfo;
import com.ecommerce.admins.entity.AdminRole;
import com.ecommerce.admins.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.ecommerce.common.enums.AdminStatus;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 회원가입: 리퀘스트 값을 받아 비밀번호 암호화 이후 서버에 저장
	 * @param request 이름, 메일, 비번, 전화번호, 역할
	 */
	@Transactional
	public void save(@Valid CreateAdminRequest request) {
		if (adminRepository.existsByEmail(request.getEmail())) {
			throw new IllegalArgumentException("이미 사용중인 메일입니다.");
		}
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		adminRepository.save(new Admin(request.getName(), request.getEmail(), encodedPassword, request.getPhoneNumber(),
			request.getRole()));
	}

	/**
	 * 로그인: 메일과 비밀번호가 일치하는지, 상태값을 확인 후 로그인
	 * @param request 이메일과 로그인
	 * @return 세션값 저장 후 반환
	 */
	@Transactional(readOnly = true)
	public AdminInfo login(@Valid LoginAdminRequest request) {
		Admin admin = adminRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new IllegalArgumentException("존재하는 이메일을 찾을 수 없습니다,"));

		if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
			throw new IllegalArgumentException("메일과 비밀번호가 일치하지 않습니다.");
		}
		checkStatusOrThrow(admin);

		return new AdminInfo(admin.getAdminId(), admin.getEmail(), admin.getRole());
	}

	/**
	 * 전체 관리자 조회: 슈퍼관리자, 상태를 확인 후 전체 관리자 조회
	 * @param keyword 검색할 키워드
	 * @param role 검색할 역할
	 * @param status 검색할 상태
	 * @param pageable 페이지네이션 조건
	 * @param adminInfo 검증을 위한 세션값
	 * @return 전체 관리자 반환
	 */
	@Transactional(readOnly = true)
	public Page<GetAdminResponse> getAdminList(String keyword, AdminRole role, AdminStatus status, Pageable pageable,
		AdminInfo adminInfo) {
		checkSuperAdminAndActive(adminInfo);

		return adminRepository.findAllByCondition(keyword, role, status, pageable).map(GetAdminResponse::from);
	}

	/**
	 * 특정 관리자 조회
	 * @param adminId 특정 관리자 아이디
	 * @param adminInfo 검증을 위한 세션값
	 * @return 검증 이후 특정 관리자의 이름, 메일, 전화번호, 역할, 상태, 생성일, 수락일 반환
	 */
	@Transactional(readOnly = true)
	public GetOneAdminResponse getOne(Long adminId, AdminInfo adminInfo) {
		checkSuperAdminAndActive(adminInfo);

		Admin admin = findByIdOrThrow(adminId);

		return GetOneAdminResponse.from(admin);
	}

	/**
	 * 관리자 정보 수정
	 * @param adminId 수정할 관리자 아이디
	 * @param request 수정할 정보
	 * @param adminInfo 검증을 위한 세션값
	 */
	@Transactional
	public void update(Long adminId, UpdateAdminRequest request, AdminInfo adminInfo) {
		checkSuperAdminAndActive(adminInfo);

		Admin admin = findByIdOrThrow(adminId);

		admin.updateAdmin(request.getName(), request.getEmail(), request.getPhoneNumber());
	}

	/**
	 * 관리자 역할 변경
	 * @param adminId 변경할 관리자 아이디
	 * @param request 변경할 역할
	 * @param adminInfo 검증을 위한 세션값
	 */
	@Transactional
	public void updateRole(Long adminId, @Valid UpdateRoleAdminRequest request, AdminInfo adminInfo) {
		checkSuperAdminAndActive(adminInfo);

		Admin admin = findByIdOrThrow(adminId);

		admin.updateRole(request.getRole());
	}

	/**
	 * 관리자 상태 변경
	 * @param adminId 변경할 관리자 아이디
	 * @param request 변경할 상태
	 * @param adminInfo 검증을 위한 세션값
	 */
	@Transactional
	public void updateStatus(Long adminId, @Valid UpdateStatusAdminRequest request, AdminInfo adminInfo) {
		checkSuperAdminAndActive(adminInfo);

		Admin admin = findByIdOrThrow(adminId);

		admin.updateStatus(request.getStatus());
	}

	/**
	 * 관리자 삭제
	 * @param adminId 삭제할 관리자 아이디
	 * @param adminInfo 검증을 위한 세션값
	 */
	@Transactional
	public void delete(Long adminId, AdminInfo adminInfo) {
		checkSuperAdminAndActive(adminInfo);

		Admin admin = findByIdOrThrow(adminId);

		admin.softDelete();
	}

	/**
	 * 아이디를 찾아 없을시 예외를 던지는 메서드
	 * @param adminId 찾을 아이디
	 * @return 예외처리를 마친 어드민 값
	 */
	public Admin findByIdOrThrow(Long adminId) {
		return adminRepository.findById(adminId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
	}

	/**
	 * 활성화 외 접근 불가 메서드
	 * @param admin 검증을 위한 어드민 값
	 */
	public void checkStatusOrThrow(Admin admin) {
		switch (admin.getStatus()) {
			case INACTIVE -> throw new IllegalArgumentException("계정 비활성화됨");
			case SUSPENDED -> throw new IllegalArgumentException("계정 정지됨");
			case PENDING -> throw new IllegalArgumentException("계정 승인대기중");
			case REJECTED -> throw new IllegalArgumentException("계정 신청 거부됨");
		}
	}

	/**
	 * 슈퍼어드민, 활성상태를 검증
	 * @param adminInfo 검증을 위한 세션값
	 */
	public void checkSuperAdminAndActive(AdminInfo adminInfo) {
		if (!adminInfo.getRole().equals(AdminRole.SUPER_ADMIN)) {
			throw new IllegalArgumentException("권한이 없습니다");
		}
		Admin requester = findByIdOrThrow(adminInfo.getAdminId());
		checkStatusOrThrow(requester);
	}

}
