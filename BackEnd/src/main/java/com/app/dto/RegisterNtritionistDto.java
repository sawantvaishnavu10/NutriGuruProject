package com.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegisterNtritionistDto {
	private String name;
	private String email;
	private String password;
	private double consulatationFees;
	private String Qualification;
	private String category;
	private String role;
}
