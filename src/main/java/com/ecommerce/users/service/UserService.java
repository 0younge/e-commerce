package com.ecommerce.users.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.users.dto.GetUserResponse;
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
}
