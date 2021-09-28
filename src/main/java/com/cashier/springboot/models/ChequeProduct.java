package com.cashier.springboot.models;

import java.math.BigDecimal;

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
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity(name = "cheques_products")
@Table(name = "cheques_products")
public class ChequeProduct {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cheque_id", referencedColumnName = "id", nullable = false)
	private Cheque cheque;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
	private Product product;
	
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
	private BigDecimal price;
    
	@NotNull
	@Min(0)
	private int amount;
	
	public BigDecimal getTotalPrice() {
		return price.multiply(BigDecimal.valueOf(amount));
	}
}
