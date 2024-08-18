package com.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.app.custom_exception.AlreadyExistsException;
import com.app.custom_exception.SlotsUnavailable;
import com.app.dto.AppointmentDTO;
import com.app.entity.Appointment;
import com.app.entity.Client;
import com.app.entity.Nutritionist;
import com.app.entity.Slot;
import com.app.entity.User;
import com.app.repository.AppointmentRepository;
import com.app.repository.ClientRepository;
import com.app.repository.NutritionistRepository;
import com.app.repository.UserRepository;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private NutritionistRepository nutritionistRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private JavaMailSender mailSender;

	public Slot checkAvailableSlots(Slot slot, LocalDate date, Nutritionist nutritionist) throws SlotsUnavailable {
		// find count in Nutritionist table matched by id,date,Slot
		Integer count = nutritionistRepository.findCountByNutrtionistIdAndDateAndTimeSlot(nutritionist.getId(), date,
				slot);
		System.out.println("get count " + count);
		if (count == null) {
			count = 0; // Initialize count if it's null
		}

		if (count >= 3) {
			throw new SlotsUnavailable("Slots Unavailable");
		}
		return slot;
	}

	@Override
	@Transactional
    @Retryable(value = DeadlockLoserDataAccessException.class, maxAttempts = 3)
	public AppointmentDTO bookAppointment(Long userId, Long nutritionistId, String date, String timeSlot)
			throws SlotsUnavailable, AlreadyExistsException {

		Optional<User> user = userRepository.findById(userId);
		Optional<Nutritionist> nutritionist = nutritionistRepository.findById(nutritionistId);

		LocalDate bookedDate = LocalDate.parse(date);

		// get client by appointment date and find its email and check with user email
		List<Appointment> c1 = appointmentRepository.findByDate(bookedDate);
		for (Appointment a : c1) {
			String email = a.getClient().getEmail();
			System.err.println("em " + email + "uemail " + user.get().getEmail());
			if (email.equals(user.get().getEmail())) {
				System.out.println("email check");
				throw new AlreadyExistsException("alredy???");
			}
		}

//		Optional<Client> clientOptional = c1.stream().filter(app -> user.get().getEmail().equals(client.getEmail()))
//				.findFirst();
//		if (clientOptional.isPresent()) {
//			throw new AlreadyExistsException("alredy???");
//		}

		Slot slot = Slot.valueOf(timeSlot.toUpperCase());

		slot = checkAvailableSlots(slot, bookedDate, nutritionist.get());
		if (slot == Slot.AFTERNOON) {
			nutritionist.get().setAfternoonAppointmentCount(nutritionist.get().getAfternoonAppointmentCount() + 1);
		} else if (slot == Slot.MORNING) {
			nutritionist.get().setMorningAppointmentCount(nutritionist.get().getMorningAppointmentCount() + 1);
		} else if (slot == Slot.EVENING) {
			nutritionist.get().setEveningAppointmentCount(nutritionist.get().getEveningAppointmentCount() + 1);

		}
		Slot bookedSlot = Slot.valueOf(timeSlot.toUpperCase());

		// Client client = clientRepository.findById(userId).orElseGet(() -> {
		Client newClient = new Client();
		newClient.setId(user.get().getId());
		newClient.setName(user.get().getName());
		newClient.setEmail(user.get().getEmail());
		newClient.setPassword(user.get().getPassword());
		newClient.setContact(user.get().getContact());
		newClient.setAge(user.get().getAge());
		newClient.setTimeSlot(bookedSlot.getDescription().toString());
		newClient.setAddress(user.get().getAddress());
		newClient.setDob(user.get().getDob());
		newClient.setNutritionist(nutritionist.get());
		clientRepository.save(newClient);

		// });

		// Ensure the bookAppointment list is initialized
		if (newClient.getBookAppointment() == null) {
			newClient.setBookAppointment(new ArrayList<>()); // Initialize if null
		}

		Appointment appointment = new Appointment();
		appointment.setNutritionist(nutritionist.get());
		appointment.setDate(bookedDate);
		appointment.setTimeSlot(slot);
		appointment.setClient(newClient);
		appointmentRepository.save(appointment);

		nutritionist.get().getAppointments().add(appointment);
		nutritionist.get().setAppointmentDate(appointment.getDate());
		nutritionistRepository.save(nutritionist.get());

		// Save client once after adding the appointment
		newClient.getBookAppointment().add(appointment);
		newClient.addAppointment(appointment);
		clientRepository.save(newClient);

		AppointmentDTO appointmentDTO = mapper.map(appointment, AppointmentDTO.class);
		appointmentDTO.setClientName(newClient.getName());
		appointmentDTO.setClientId(newClient.getId());
		appointmentDTO.setClientEmail(newClient.getEmail());
		appointmentDTO.setNutritionistId(nutritionist.get().getId());
		appointmentDTO.setNutritionistName(nutritionist.get().getName());
		appointmentDTO.setEmail(nutritionist.get().getEmail());

		// Send confirmation email to the client
		 sendAppointmentConfirmationEmail(newClient, appointmentDTO);

		return appointmentDTO;
	}
	
	  private void sendAppointmentConfirmationEmail(Client client, AppointmentDTO
	  appointmentDTO) { String to = client.getEmail(); String subject =
	  "Appointment Confirmation"; String body = "Dear " + client.getName() +
	  ",\n\n" + "Your appointment with " + appointmentDTO.getNutritionistName() +
	 " is confirmed.\n" + "Details:\n" + "Date: " + appointmentDTO.getDate() +
	  "\n" + "Time: " + appointmentDTO.getTimeSlot() + "\n\n" +
	  "Thank you for choosing our services!\n" + "Best regards,\n" +
	  "NutriGuru";
	
	sendEmail(to, subject, body);
	}
	 
//	
//	  private void sendEmail(String to, String subject, String body) {
//	  SimpleMailMessage message = new SimpleMailMessage();
//	  message.setTo(to);
//	  message.setSubject(subject);
//	  message.setText(body); 
//	  mailSender.send(message);
//	  }
	  
	  private void sendEmail(String to, String subject, String body) {
		    try {
		        SimpleMailMessage message = new SimpleMailMessage();
		        message.setTo(to);
		        message.setSubject(subject);
		        message.setText(body); 
		        System.out.println("Sending email to " + to);  // Debugging statement
		        mailSender.send(message);
		        System.out.println("Email sent successfully!");  // Debugging statement
		    }
		    catch (Exception e) {
		        System.err.println("Failed to send email: " + e.getMessage());  // Log the error
		    }
	  }

	@Override
	public List<AppointmentDTO> getAllAppointmentsByNutritionist(Long id) {
		
	    List<Appointment> appointments = appointmentRepository.findAllByNutritionistId(id);
	    return appointments.stream()
	            .map(appointment -> {
	                AppointmentDTO dto = new AppointmentDTO();
	                dto.setId(appointment.getId());
	                dto.setDate(appointment.getDate());
	                dto.setTimeSlot(appointment.getTimeSlot());
	                dto.setClientId(appointment.getClient().getId());
	                dto.setClientName(appointment.getClient().getName());
	                dto.setClientEmail(appointment.getClient().getEmail());
	                dto.setNutritionistId(appointment.getNutritionist().getId());
	                dto.setNutritionistName(appointment.getNutritionist().getName());
	                dto.setEmail(appointment.getNutritionist().getEmail());
	                return dto;
	            })
	            .collect(Collectors.toList());
	}

}