package com.app.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.entity.Appointment;
import com.app.entity.Client;
import com.app.entity.Nutritionist;
import com.app.entity.Slot;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	List<Appointment> findByNutritionistAndDate(Nutritionist nutri, LocalDate date);

	Appointment findByClientAndDate(Client client, LocalDate bookedDate);

	List<Appointment> findByDate(LocalDate date);

	@Query("select a from Appointment a where a.nutritionist.id = :id")
	List<Appointment> findAllByNutritionistId(@Param("id") Long id);

}
