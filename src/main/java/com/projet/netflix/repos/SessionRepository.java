package com.projet.netflix.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.projet.netflix.entities.Session;

@EnableJpaRepositories
@Repository
@RepositoryRestResource(collectionResourceRel = "session",path = "session")
public interface SessionRepository extends JpaRepository<Session, Long> {

	
	@Query("SELECT s FROM Session s WHERE s.utilisateur.id = :Id")
	List<Session> findAllSessionByUserId(@Param("Id") Long Id);
	

}
