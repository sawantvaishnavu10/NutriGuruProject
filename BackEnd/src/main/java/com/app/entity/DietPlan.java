package com.app.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DietPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	@Enumerated(EnumType.STRING)
	private DietPlanProgram program;
	@OneToOne
	private Appointment appointment;

	// One Diet plan belongs to one client
	// @OneToOne
	// @JoinColumn(name="client_Id")
	// private Client client;

//	@ManyToOne
//	@JoinColumn(name="nutritionist_Id")
//	private Nutritionist nutritionist;
}
