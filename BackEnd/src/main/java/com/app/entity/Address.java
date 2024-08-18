package com.app.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Address {	
	
	private String addressLine;
	private String state;
	@Column(nullable = true)
	private int coutryCode;
	@Column(nullable = true)
	private int zipCode;
	
}
