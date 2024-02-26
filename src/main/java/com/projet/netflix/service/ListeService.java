package com.projet.netflix.service;

import java.util.List;
import java.util.Optional;

import com.projet.netflix.dto.ListeDTO;
import com.projet.netflix.entities.Liste;


public interface ListeService {
	public ListeDTO saveIdFilm(ListeDTO l);
	public ListeDTO getIdFilm(Long idFilm);
	public List<ListeDTO> getAllFilms();
	Optional<Liste> findByIdFilm(Long idFilm);
	void deleteFilmByIdFilm(Long idFilm);
	
//	ListeDTO convertEntityToDto(Liste liste);
//	Liste convertDtoToEntity(ListeDTO listeDto);

}
