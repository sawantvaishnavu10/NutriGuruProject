package com.app.dto;

import java.time.LocalDate;

import javax.persistence.Embedded;

import com.app.entity.Address;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegisterUserDTO {

	private String name;
	private String email;
	private String password;
	private String contact;
	private int age;
	private LocalDate dob;
	@Embedded
	private Address address;	
	private String role;
}
