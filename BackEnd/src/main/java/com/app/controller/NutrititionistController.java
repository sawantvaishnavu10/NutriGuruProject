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
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AppoimnetForTodayDto;
import com.app.dto.DietPlanDto;
import com.app.dto.NutritionistDto;
import com.app.dto.RegisterNtritionistDto;
import com.app.service.NutritionistService;

@RestController
@RequestMapping("/nutritionist")
public class NutrititionistController {

	@Autowired
	private NutritionistService nutritionistService;

	@GetMapping("/getAllNutritionist")

	public ResponseEntity<?> getAllNutritionist() {
		List<NutritionistDto> nutriList = nutritionistService.getAllNutritionist();

		return ResponseEntity.status(HttpStatus.OK).body(nutriList);
	}

	// Get all appoinmnets for today
	@GetMapping("/getAllAppoinmnetForToday/{nutritionistId}")
	public ResponseEntity<?> getAllAppoinmnetForToday(@PathVariable Long nutritionistId) {
		List<AppoimnetForTodayDto> appoimnet = nutritionistService.getAllAppoinmnetForToday(nutritionistId);
		return ResponseEntity.status(HttpStatus.OK).body(appoimnet);
	}

	@PostMapping("/setDietPlan/{clientId}")

	public ResponseEntity<?> addDietPlan(@PathVariable Long clientId, @RequestBody DietPlanDto dietPlanDto) {
		return ResponseEntity.status(HttpStatus.OK).body(nutritionistService.addDietPlan(clientId, dietPlanDto));
	} 
	
	 @GetMapping("/getNutriIdByEmail/{email}")
	    public ResponseEntity<?> getNutriIdByEmail(@PathVariable String email) {
	        Long nutriId = nutritionistService.getIdByEmail(email);
	        return ResponseEntity.status(HttpStatus.OK).body(nutriId);
	    }
	 @PostMapping("/registerNutritionist")
		public ResponseEntity<?> addNutritionist(@RequestBody RegisterNtritionistDto ntritionistDto){
			
			
			return ResponseEntity.ok(nutritionistService.addNutritionist(ntritionistDto));
		}
}
