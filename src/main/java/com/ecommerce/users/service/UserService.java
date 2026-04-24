package com.ecommerce.users.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.users.dto.GetUserResponse;
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
	public GetUserResponse findUserDetails(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalStateException("해당 유저를 찾을 수 없습니다."));

		return new GetUserResponse(
			user.getUserId(),
			user.getName(),
			user.getEmail(),
			user.getPhoneNumber(),
			user.getStatus().name(),
			user.getCreatedAt());
	}
}
