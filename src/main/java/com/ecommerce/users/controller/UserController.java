package com.ecommerce.users.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.users.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

}
