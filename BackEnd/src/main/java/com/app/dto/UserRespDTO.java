package com.app.dto;

import java.time.LocalDate;

import javax.persistence.Embedded;

import com.app.entity.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//firstname , last name,phone no , dob
@Getter
@Setter
@ToString
public class UserRespDTO {
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	private String name;
	private String email;
	private LocalDate dob;
	private String contact;
	private int age;
	@Embedded
	private Address address;
	private String role;
}
