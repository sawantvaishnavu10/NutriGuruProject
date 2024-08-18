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

import com.app.dto.ClientDto;
import com.app.dto.RegisterClientDto;
import com.app.service.ClientService;


@RestController
@RequestMapping("/client")
public class ClientController {
	//depedency
	
	 @Autowired 
	 private ClientService clientService;
	 
	  //get All client
	  
	  @GetMapping("/getAllClient") 
	  public ResponseEntity<?> getAllClient(){
	  List<ClientDto> clients=clientService.getAllClient();
	 
	  return ResponseEntity.status(HttpStatus.OK).body(clients);
	 
	  }
	 

	// bookAppoinmet
	// 1.-> show all nutritionist 2.-> show all available timeslots 3.-> payment
//show all time slots to the client 
	
 @GetMapping("/getDietPlan/{clientId}")
 public ResponseEntity<?> getDietPlan(@PathVariable Long clientId){
	 System.out.println("recived c id "+clientId);
	 return ResponseEntity.status(HttpStatus.OK).body(clientService.getDietPlan(clientId));
 }
 
 @GetMapping("/showAllTimeSlots")
 public ResponseEntity<?> showAllTimeSlots(){
	return ResponseEntity.status(HttpStatus.OK).body(clientService.getAllTimeSlots()); 
 }
 
 @PostMapping("/register")
 public ResponseEntity<?> registerClient(@RequestBody RegisterClientDto regClientDto){
	RegisterClientDto newClient= clientService.registerClient(regClientDto);
	return ResponseEntity.status(HttpStatus.OK).body("You are registered: "+newClient);
 }
}
