package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.custom_exception.AlreadyExistsException;
import com.app.custom_exception.NoAppointments;
import com.app.custom_exception.SlotsUnavailable;
import com.app.dto.AppointmentDTO;
import com.app.dto.BookAppointmentDTO;
import com.app.entity.Nutritionist;
import com.app.service.AppointmentService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	AppointmentService appointmentService;

	@PostMapping("/bookAppointment")
	public ResponseEntity<?> bookAppointment(@RequestBody BookAppointmentDTO appointment)
			throws SlotsUnavailable, AlreadyExistsException {
		System.out.println("data at be " + appointment);
		AppointmentDTO a = appointmentService.bookAppointment(appointment.getUserId(), appointment.getNutritionistId(),
				appointment.getDate(), appointment.getTimeSlot());
		if (a == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(a);
		}
		return ResponseEntity.status(HttpStatus.OK).body(a);
	}

	@GetMapping("/getAll/{id}")
	public ResponseEntity<?> getAllAppointmentsByNutritionistId(@PathVariable Long id) {
		try {
			List<AppointmentDTO> appointmentDtoList = appointmentService.getAllAppointmentsByNutritionist(id);
			if (appointmentDtoList == null) {
				throw new NoAppointments("No appointments");
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(appointmentDtoList);
			}
		} catch (NoAppointments e) {
			System.out.println("No appointments");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

		}
	}
	
}
