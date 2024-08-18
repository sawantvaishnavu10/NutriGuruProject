package com.app.dto;

import java.time.LocalDate;

import com.app.entity.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class RegisterClientDto {
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	private String name;
	private String email;
	private String password;
	private String contact;
	private int age;
	private LocalDate dob;
	private Address address;
	
}
