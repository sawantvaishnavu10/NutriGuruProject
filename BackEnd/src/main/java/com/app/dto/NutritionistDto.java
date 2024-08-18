package com.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class NutritionistDto {

	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	private String name;
	private String email;
	private String password;
	private double consulatationFees;
	private String Qualification;
	private double rating;
	private String categoryName;
	private int MorningAppointmentCount;
	private int AfternoonAppointmentCount;
	private int EveningAppointmentCount;
	
}
