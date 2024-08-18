package com.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookAppointmentDTO {

	 	private Long userId;
	    private Long nutritionistId;
	    private String date;
	    private String timeSlot;
}
