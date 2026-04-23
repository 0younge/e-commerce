package com.ecommerce.users.service;

import org.springframework.stereotype.Service;

import com.ecommerce.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

}
