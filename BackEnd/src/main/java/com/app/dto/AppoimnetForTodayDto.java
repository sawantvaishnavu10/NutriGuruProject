package com.app.dto;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.app.entity.Slot;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppoimnetForTodayDto {
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;

	private LocalDate date;
	@Enumerated(EnumType.STRING)
	private Slot timeSlot;
    
    private Long nutritionistId;
    private String nutritionistName;
    private String email;

    private Long clientId;
    private String clientName;
    private String clientEmail;
    
}
