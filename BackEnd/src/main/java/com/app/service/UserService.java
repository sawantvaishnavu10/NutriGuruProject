package com.app.service;

import com.app.dto.AuthDTO;
import com.app.dto.RegisterUserDTO;
import com.app.dto.ResponseRegisterDto;
import com.app.dto.UserRespDTO;

public interface UserService {

	UserRespDTO authenticateUser(AuthDTO dto);

	ResponseRegisterDto registerUser(RegisterUserDTO dto);

	UserRespDTO getByEmail(String email);

}
