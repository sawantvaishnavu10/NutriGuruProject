package com.app.service;

import java.time.LocalDate;
import java.util.List;

import com.app.custom_exception.AlreadyExistsException;
import com.app.custom_exception.SlotsUnavailable;
import com.app.dto.AppointmentDTO;
import com.app.entity.Nutritionist;
import com.app.entity.Slot;

public interface AppointmentService {

	AppointmentDTO bookAppointment(Long userId, Long nutritionistId, String date, String timeSlot)
			throws SlotsUnavailable, AlreadyExistsException;

	List<AppointmentDTO> getAllAppointmentsByNutritionist(Long id);

	public Slot checkAvailableSlots(Slot slot, LocalDate date, Nutritionist nutritionist) throws SlotsUnavailable;


}
