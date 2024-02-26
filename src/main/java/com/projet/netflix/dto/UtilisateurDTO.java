package com.projet.netflix.dto;

import java.util.Date;

import com.projet.netflix.entities.Session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //alternative a l'opérateur new
public class UtilisateurDTO {
	private Long idUser;
	private String email;
	private String lastName;
	private String firstName;
	private String password;
	private String age;

}
//les nom doivent etre très similaire pour que spring puisse les mapper au entité et a la bdd 
