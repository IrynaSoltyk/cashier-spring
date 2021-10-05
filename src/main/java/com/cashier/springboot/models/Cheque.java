package com.cashier.springboot.models;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity(name = "cheques")
@Table(name = "Cheques")
public class Cheque {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
    @JoinColumn(name = "created_by")
	private User createdBy;
	
	@ManyToOne
	@JoinColumn(name = "shift_id_opened")
	private Shift shiftIdOpened;
	
	@ManyToOne
	@JoinColumn(name = "shift_id_cancelled")
	private Shift shiftIdCancelled;
	
	@Column(name ="created_date", nullable = true)
	private Instant dateCreated;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "cheque_id")
	private List<ChequeProduct> products;
	
	@Column(nullable = true)
	private Instant cancelledDate;
	
	@ManyToOne
    @JoinColumn(name = "cancelled_by")
	private User cancelledBy;
	
	@Transient	
	public BigDecimal getCost() {
		return products.stream().map(a -> a.getTotalPrice()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
	}
}
