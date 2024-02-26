package com.projet.netflix.dto;


import com.projet.netflix.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionDTO {

	private Long idSession;
	private String nomSession;
	
	private User utilisateur;
}
