package com.cashier.springboot.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "users")
@Table(name = "users")
public class User implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String username;

	@Column(unique = true, nullable = false)
	private String login;
 
	@NotNull
	private String password;
	
	@NotNull
	private boolean enabled;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
            )
	private Set<Role> roles = new HashSet<>();
	
	@Transient
	public boolean hasRole(String role) {
		return  getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(role));
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
