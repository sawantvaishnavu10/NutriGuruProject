package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AuthDTO;
import com.app.dto.RegisterUserDTO;
import com.app.dto.ResponseRegisterDto;
import com.app.dto.UserRespDTO;
import com.app.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
//dependecy
	@Autowired
	private UserService userService;

	@PostMapping("/signIn")
	public ResponseEntity<?> userSignIn(@RequestBody AuthDTO dto) {
		System.out.println("in sigin user " + dto);
		try {
			UserRespDTO respDto = userService.authenticateUser(dto);
			return ResponseEntity.ok(respDto);
		} catch (RuntimeException e) {
			// invalid login
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
		}
	}

	@PostMapping("/signUp")
	public ResponseEntity<?> userRegister(@RequestBody RegisterUserDTO registerUserDTO) {
		System.out.println("in sigup user " + registerUserDTO);
		try {
			ResponseRegisterDto respDto = userService.registerUser(registerUserDTO);
			return ResponseEntity.ok(respDto);
		} catch (RuntimeException e) {
			// If null received
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not registered");
		}
	}

	@GetMapping("/findByEmail")
	public ResponseEntity<?> findByEmail(@RequestParam String email) {
		System.out.println("Email received " + email);
		try {
			UserRespDTO respDto = userService.getByEmail(email);
			System.out.println("resp " + respDto);
			return ResponseEntity.ok(respDto);
		} catch (RuntimeException e) {
			// If null received
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

	}
}
