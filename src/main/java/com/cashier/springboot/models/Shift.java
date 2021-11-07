package com.cashier.springboot.models;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="shifts")
@Table(name="shifts")
public class Shift {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	private Instant beginDate;
	
	@Column(nullable = true)
	private Instant endDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;

}
