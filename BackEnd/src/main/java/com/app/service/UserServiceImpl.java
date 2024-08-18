package com.app.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.custom_exception.InvalidClientId;
import com.app.dto.AuthDTO;
import com.app.dto.RegisterUserDTO;
import com.app.dto.ResponseRegisterDto;
import com.app.dto.UserRespDTO;
import com.app.entity.Role;
import com.app.entity.User;
import com.app.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper mapper;

	@Override
	public UserRespDTO authenticateUser(AuthDTO dto) {
		BCryptPasswordEncoder bCryptPasswordEncoder =new BCryptPasswordEncoder();
		Optional<User> user = userRepository.findByEmail(dto.getEmail());
		// map entity -> DTO
		System.out.println("received user "+user);
		System.out.println("decrypting");
		System.out.println("incoming pass"+dto.getPassword());
		System.out.println("db pass"+user.get().getPassword());

		if(!bCryptPasswordEncoder.matches(dto.getPassword(), user.get().getPassword())) {
			throw new InvalidClientId("pass did not matched");
		}

		return mapper.map(user.get(), UserRespDTO.class);
	}

	@Override
	public ResponseRegisterDto registerUser(RegisterUserDTO user) {
		BCryptPasswordEncoder bCryptPasswordEncoder =new BCryptPasswordEncoder();
		String encryptedPassword=bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		Role role = Role.valueOf(user.getRole().toUpperCase());
		User newUser = mapper.map(user, User.class);
		newUser.setRole(role);
		userRepository.save(newUser);
		return mapper.map(newUser, ResponseRegisterDto.class);
	}

	@Override
	public UserRespDTO getByEmail(String email) {
		System.out.println("email " + email);
		Optional<User> u = userRepository.findByEmail(email);
		System.out.println("user u " + u.get());
		return mapper.map(u.get(), UserRespDTO.class);
	}
}
