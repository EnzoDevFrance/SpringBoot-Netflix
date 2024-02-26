package com.projet.netflix.restcontrollers;

import java.net.URI;
import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.projet.netflix.dto.UserDto;
import com.projet.netflix.dto.UtilisateurDTO;
import com.projet.netflix.entities.User;
import com.projet.netflix.mappers.UserMapper;
import com.projet.netflix.repos.UtilisateurRepository;
import com.projet.netflix.service.UtilisateurService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UtilisateurRESTController {
	
	private final UtilisateurService utilisateurService;

    //@PostMapping("/signin")
	


	
	@RequestMapping(method=RequestMethod.GET)
	List<UserDto> getAllUtilisateurs()
	{
		return utilisateurService.getAllUtilisateurs();
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public UserDto getUtilisateurById(@PathVariable("id") Long id)
	 {
		return utilisateurService.getUtilisateur(id);
	 }
	
	
	@RequestMapping(method = RequestMethod.PUT)
	public UtilisateurDTO updateUtilisateur(@RequestBody UtilisateurDTO utilisateurDTO) 
	{
	return utilisateurService.updateUtilisateur(utilisateurDTO);
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)
	public void deleteUtilisateur(@PathVariable("id") Long id)
	{
		utilisateurService.deleteUtilisateurById(id);
	}
	
}

