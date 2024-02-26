package com.projet.netflix.repos;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projet.netflix.entities.Liste;

	@Repository
	public interface ListRepository extends JpaRepository<Liste, Long>{
	
		@Query("SELECT m FROM Liste m WHERE m.idFilm = :idFilm")
		Optional<Liste> findByIdFilm(@Param("idFilm") Long idFilm);
	}

