package com.app.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"appointment","clients","category"})

public class Nutritionist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;
	private double consulatationFees;
	private String Qualification;
	private double rating;
	private int MorningAppointmentCount;
	private int AfternoonAppointmentCount;
	private int EveningAppointmentCount;
	private LocalDate appointmentDate;

	// One nutritionist belongs to many client
	@OneToMany(mappedBy = "nutritionist", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Client> clients = new ArrayList<Client>();

	// Many nutritionist belongs to one category
	@ManyToOne
	@JoinColumn(name = "category_Id")
	@JsonIgnore
	private Category category;
	
	//helper mathod
	//client has set nutritionist id and book appoinmnet
	public void bookClient(Client client) {
		clients.add(client);
		client.setNutritionist(this);
	}
	//for cancelling appoinmnet client remove nutri id
	public void removeClient(Client client) {
		clients.remove(client);
		client.setNutritionist(null);
	}
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Appointment> appointments;
}
