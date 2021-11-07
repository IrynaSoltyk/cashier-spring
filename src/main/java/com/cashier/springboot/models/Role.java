package com.cashier.springboot.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "roles")
@Table(name = "roles")
public class Role  implements GrantedAuthority  {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique = true, nullable = false)
    private String name;

	@Override
	public String getAuthority() {
		 return getName();
	}
}