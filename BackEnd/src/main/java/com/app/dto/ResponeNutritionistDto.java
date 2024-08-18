package com.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponeNutritionistDto {
	private String name;
	private String email;
	
	private double consulatationFees;
	private String Qualification;
	private String category;
}
