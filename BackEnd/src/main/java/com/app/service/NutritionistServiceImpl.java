package com.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exception.ResourceNotFound;
import com.app.dto.AppoimnetForTodayDto;
import com.app.dto.DietPlanDto;
import com.app.dto.NutritionistDto;
import com.app.dto.RegisterNtritionistDto;
import com.app.dto.ResponeNutritionistDto;
import com.app.entity.Appointment;
import com.app.entity.Category;
import com.app.entity.Client;
import com.app.entity.DietPlan;
import com.app.entity.DietPlanProgram;
import com.app.entity.Nutritionist;
import com.app.entity.Role;
import com.app.entity.User;
import com.app.repository.AppointmentRepository;
import com.app.repository.CategoryRepository;
import com.app.repository.ClientRepository;
import com.app.repository.DietPlanRepository;
import com.app.repository.NutritionistRepository;
import com.app.repository.UserRepository;

//getAllAppoinmnet
@Service
@Transactional
public class NutritionistServiceImpl implements NutritionistService {
//dependecy
	@Autowired
	private NutritionistRepository nutritionistRepository;

	@Autowired
	private AppointmentRepository appoinmnetRepository;
	@Autowired
	private ModelMapper mapper;

	@Autowired ClientRepository clientRepository;
	// Find all nutritionists
	
	@Autowired 
	DietPlanRepository dietPlanRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Override
	public List<NutritionistDto> getAllNutritionist() {

		return nutritionistRepository.findAll().stream().map(nutritionist -> {
			NutritionistDto nutriDto = new NutritionistDto();
			nutriDto.setId(nutritionist.getId());
			nutriDto.setName(nutritionist.getName());
			nutriDto.setEmail(nutritionist.getEmail());
			nutriDto.setConsulatationFees(nutritionist.getConsulatationFees());
			nutriDto.setQualification(nutritionist.getQualification());
			nutriDto.setRating(nutritionist.getRating());
			nutriDto.setCategoryName(nutritionist.getCategory().getName());
			return nutriDto;
		}).collect(Collectors.toList());

	}

	// Get all appointments for today by nutritionists Id
	@Override
	public List<AppoimnetForTodayDto> getAllAppoinmnetForToday(Long nutritionistId) {
		Optional<Nutritionist> nutritionist = nutritionistRepository.findById(nutritionistId);
		Nutritionist nutri = nutritionist.get();
		LocalDate today = LocalDate.now();
		List<Appointment> appointmentsForToday = appoinmnetRepository.findByNutritionistAndDate(nutri, today);
		// list of appoinmnet
		List<AppoimnetForTodayDto> appoimnetForTodayDtos = new ArrayList<>();
		// attach for loop for get all the appoimnets for today

		for (Appointment appointment : appointmentsForToday) {
			AppoimnetForTodayDto appoimnetForTodayDto = new AppoimnetForTodayDto();
			appoimnetForTodayDto.setId(appointment.getId());
			appoimnetForTodayDto.setDate(appointment.getDate());
			appoimnetForTodayDto.setTimeSlot(appointment.getTimeSlot());
			appoimnetForTodayDto.setNutritionistId(appointment.getNutritionist().getId());
			appoimnetForTodayDto.setNutritionistName(appointment.getNutritionist().getName());
			appoimnetForTodayDto.setClientId(appointment.getClient().getId());
			appoimnetForTodayDto.setClientName(appointment.getClient().getName());
			appoimnetForTodayDto.setClientEmail(appointment.getClient().getEmail());
			appoimnetForTodayDto.setEmail(appointment.getNutritionist().getEmail());
			appoimnetForTodayDtos.add(appoimnetForTodayDto);
		}
		return appoimnetForTodayDtos;
	}

	@Override
	public DietPlanDto addDietPlan(Long clientId, DietPlanDto dietPlanDto) {
		System.out.println("cid "+clientId+" d "+dietPlanDto);
		Optional<Client> client=clientRepository.findById(clientId);
		  if (!client.isPresent()) {
		        throw new ResourceNotFound("Client not found with ID: " + clientId);
		    }
		Client c=client.get();
		
		DietPlan dietPlan=new DietPlan();
		dietPlan.setDescription(dietPlanDto.getDescription());
		dietPlan.setProgram(DietPlanProgram.valueOf(dietPlanDto.getProgram().toUpperCase()));
		dietPlan.setAppointment(c.getBookAppointment().get(0));
		c.setDietPlan(dietPlan);
		dietPlanRepository.save(dietPlan);
		return mapper.map(dietPlan, DietPlanDto.class);
	}
	
	 @Override
	    public Long getIdByEmail(String email) {
	        System.out.println("Received email: " + email);
	        if (nutritionistRepository == null) {
	            throw new IllegalStateException("NutritionistRepository is not injected!");
	        }
	        Optional<Nutritionist> nutritionist = nutritionistRepository.findByEmail(email);
	            Nutritionist n=nutritionist.get();
	            Long nId=n.getId();
				/*
				 * Long nutriId = (nutritionist != null) ? nutritionist.getId() : null;
				 * System.out.println("Nutri ID from repository: " + nutriId);
				 */
	        return nId;
	    }
	 
	 
	 @Override
		public ResponeNutritionistDto addNutritionist(RegisterNtritionistDto ntritionistDto) {
		 	BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
		 	String encodedPassword=bCryptPasswordEncoder.encode(ntritionistDto.getPassword());
		 	ntritionistDto.setPassword(encodedPassword);
		 	
			Role role = Role.valueOf(ntritionistDto.getRole().toUpperCase());
			Category category = categoryRepository.findByName(ntritionistDto.getCategory())
				    .orElseThrow(() -> new RuntimeException("Category '" + ntritionistDto.getCategory() + "' not found. Please ensure the category exists."));
			//it save in user 
			User user=new User();
			user.setName(ntritionistDto.getName());
			user.setEmail(ntritionistDto.getEmail());
			user.setPassword(ntritionistDto.getPassword());
			user.setRole(role);
			
			User savedUser=userRepository.save(user);
		        Nutritionist nutritionist = new Nutritionist();
		        nutritionist.setName(ntritionistDto.getName());
		        nutritionist.setEmail(ntritionistDto.getEmail());
		        nutritionist.setPassword(ntritionistDto.getPassword());
		        nutritionist.setConsulatationFees(ntritionistDto.getConsulatationFees());
		        nutritionist.setQualification(ntritionistDto.getQualification());
		        nutritionist.setCategory(category);
		        
		        Nutritionist savedNutritionist = nutritionistRepository.save(nutritionist);
		        
		        // Map the saved nutritionist to ResponeNutritionistDto
		        ResponeNutritionistDto response = new ResponeNutritionistDto();
		        response.setName(savedNutritionist.getName());
		        response.setEmail(savedNutritionist.getEmail());
		        response.setConsulatationFees(savedNutritionist.getConsulatationFees());
		        response.setQualification(savedNutritionist.getQualification());
		        response.setCategory(category.getName());

		        return response;
			
		}
	

}
