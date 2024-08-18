package com.app.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.dto.NutritionistDto;
import com.app.entity.Nutritionist;
import com.app.entity.Slot;

@Repository
public interface NutritionistRepository extends JpaRepository<Nutritionist, Long> {

	@Query("SELECT COUNT(a) FROM Appointment a WHERE a.nutritionist.id = :id AND a.date = :date AND a.timeSlot = :slot")
	Integer findCountByNutrtionistIdAndDateAndTimeSlot(@Param("id") Long id, @Param("date") LocalDate date,
			@Param("slot") Slot slot);

	 Optional<Nutritionist> findByEmail(String email);

}
