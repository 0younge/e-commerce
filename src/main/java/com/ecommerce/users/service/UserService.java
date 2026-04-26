package com.ecommerce.users.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.common.enums.UserStatus;
import com.ecommerce.common.exception.UserNotFoundException;
import com.ecommerce.orders.entity.Order;
import com.ecommerce.users.dto.GetOneUserResponse;
import com.ecommerce.users.dto.GetUserResponse;
import com.ecommerce.users.dto.PatchUserRequest;
import com.ecommerce.users.dto.PatchUserResponse;
import com.ecommerce.users.dto.PatchUserStatusRequest;
import com.ecommerce.users.entity.User;
import com.ecommerce.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	/**
	 * 사용자 목록 조회
	 * @param keyword 검색할 키워드
	 * @param status 검색할 상태
	 * @param pageable 페이지네이션 정보
	 * @return 페이지네이션을 마친 사용자 리스트
	 */
	@Transactional(readOnly = true)
	public Page<GetUserResponse> findByKeywordAndStatus(String keyword, UserStatus status, Pageable pageable) {
		return userRepository.findByKeywordAndStatus(keyword, status, pageable)
			.map(user -> GetUserResponse.from(user));
	}

	/**
	 * 특정 사용자 조회
	 * @param userId 조회할 사용자 아이디
	 * @return 특정 사용자의 상세 정보
	 */
	@Transactional(readOnly = true)
	public GetOneUserResponse findUserDetails(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException());

		return GetOneUserResponse.from(user);
	}

	/**
	 * 사용자 정보 수정
	 * @param userId 수정할 사용자 아이디
	 * @param patchUserRequest 수정할 값
	 * @return 수정된 사용자 정보
	 */
	@Transactional
	public PatchUserResponse patchUserDetails(Long userId, PatchUserRequest patchUserRequest) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

		user.updateDetails(patchUserRequest.getName(), patchUserRequest.getEmail(), patchUserRequest.getPhoneNumber());
		return PatchUserResponse.from(user);
	}

	/**
	 * 사용자 상태 변경
	 * @param userId 상태를 변경할 사용자 아이디
	 * @param patchUserStatusRequest 변경할 상태
	 * @return 상태가 변경된 사용자 정보
	 */
	@Transactional
	public PatchUserResponse patchUserStatus(Long userId, PatchUserStatusRequest patchUserStatusRequest) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

		user.updateStatus(patchUserStatusRequest.getUserStatus());
		return PatchUserResponse.from(user);
	}

	/**
	 * 사용자 삭제
	 * @param userId 삭제할 사용자 아이디
	 */
	@Transactional
	public void deleteById(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
		user.softDelete();
	}
}