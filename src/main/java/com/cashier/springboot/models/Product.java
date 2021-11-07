package com.cashier.springboot.models;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "products")
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    
    @Column(unique = true, nullable = true)
    @NotBlank 
	private String name;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unit_id", referencedColumnName = "id", nullable = false)
	private Unit unit;
	
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
	private BigDecimal price;
	
	@NotNull
	@Min(0)
	private int amount;

	public int getId() {
		return id;
	}

}
