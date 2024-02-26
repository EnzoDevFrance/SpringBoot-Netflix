package com.projet.netflix.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.projet.netflix.entities.User;

@EnableJpaRepositories
@Repository
@RepositoryRestResource(collectionResourceRel = "rest",path = "rest")
public interface UtilisateurRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
	
	Optional<User> findById(Long id);
	List<User> findByLastName(String lastName);
	List<User> findByLastNameContains(String lastName);
	List<User> findByOrderByLastNameAsc();

	// pas besoin car sur la meme page peu etre pour la connexion
    @Query("SELECT u.password FROM User u WHERE u.email = :email")
    String findPasswordByEmail(@Param("email") String email);
}


