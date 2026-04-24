package com.ecommerce.users.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional(readOnly = true)
	public Page<GetUserResponse> findUserByKeyword(String keyword, Pageable pageable) {
		return userRepository.findByKeyword(keyword, pageable)
			.map(user -> new GetUserResponse(
				user.getUserId(),
				user.getName(),
				user.getEmail(),
				user.getPhoneNumber(),
				user.getStatus().name(),
				user.getCreatedAt()
			));
	}

	@Transactional(readOnly = true)
	public GetOneUserResponse findUserDetails(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalStateException("해당 유저를 찾을 수 없습니다."));

		return new GetOneUserResponse(
			user.getName(),
			user.getEmail(),
			user.getPhoneNumber(),
			user.getStatus().name(),
			user.getCreatedAt());
	}

	@Transactional
	public PatchUserResponse patchUserDetails(Long userId, PatchUserRequest patchUserRequest) {
		User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("해당 유저를 찾을 수 없습니다."));

		user.updateDetails(patchUserRequest.getName(), patchUserRequest.getEmail(), patchUserRequest.getEmail());
		return new PatchUserResponse(user.getUserId(),
			user.getName(),
			user.getEmail(),
			user.getPhoneNumber(),
			user.getStatus().name(),
			user.getCreatedAt(),
			user.getModifiedAt()
		);
	}

	@Transactional
	public PatchUserResponse patchUserStatus(Long userId, PatchUserStatusRequest patchUserStatusRequest) {
		User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("해당 유저를 찾을 수 없습니다."));

		user.updateStatus(patchUserStatusRequest.getUserStatus());
		return new PatchUserResponse(user.getUserId(),
			user.getName(),
			user.getEmail(),
			user.getPhoneNumber(),
			user.getStatus().name(),
			user.getCreatedAt(),
			user.getModifiedAt()
		);
	}

	@Transactional
	public void deleteById(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
		userRepository.delete(user);
	}
}
