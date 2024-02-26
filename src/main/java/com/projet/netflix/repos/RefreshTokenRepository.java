package com.projet.netflix.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projet.netflix.entities.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	 
	  Optional<RefreshToken> findByToken(String token);
	 
	} 