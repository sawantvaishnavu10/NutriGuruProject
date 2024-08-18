package com.app.service;

import java.util.List;
import java.util.Optional;

import com.app.dto.ClientDto;
import com.app.dto.RegisterClientDto;
import com.app.entity.Client;
import com.app.entity.Slot;

public interface ClientService {

	List<ClientDto> getAllClient();

	

	ClientDto getDietPlan(Long clientId);



	List<Slot> getAllTimeSlots();



	RegisterClientDto registerClient(RegisterClientDto regClientDto);

}
