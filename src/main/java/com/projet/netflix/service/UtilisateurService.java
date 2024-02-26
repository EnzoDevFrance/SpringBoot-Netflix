package com.projet.netflix.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.projet.netflix.dto.UserDto;
import com.projet.netflix.dto.UtilisateurDTO;
import com.projet.netflix.entities.Session;
import com.projet.netflix.entities.User;


public interface UtilisateurService {
	
	
	
//UtilisateurDTO renvoie une instance de UtilisateurDTO

	UserDto getUtilisateur(Long id);
	List<UserDto> getAllUtilisateurs();

	public boolean changePassword(Long userId, String oldPassword, String newPassword);
	
	
	UtilisateurDTO updateUtilisateur(UtilisateurDTO u);
	void deleteUtilisateur(User u);
	void deleteUtilisateurById(Long id);
	
	List<User> findByNomUtilisateur(String nom);
	List<User> findByNomUtilisateurContains(String nom);
	List<User> findByOrderByNomUtilisateurAsc();
	
	

}
