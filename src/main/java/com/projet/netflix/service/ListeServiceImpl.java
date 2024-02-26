package com.projet.netflix.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projet.netflix.dto.ListeDTO;
import com.projet.netflix.dto.UtilisateurDTO;
import com.projet.netflix.entities.Liste;
import com.projet.netflix.entities.User;
import com.projet.netflix.mappers.ListMapper;
import com.projet.netflix.mappers.UserMapper;
import com.projet.netflix.repos.ListRepository;
import com.projet.netflix.repos.SessionRepository;
import com.projet.netflix.repos.UtilisateurRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class ListeServiceImpl implements ListeService{
	
	@Autowired
	ListRepository malisteRepository;
	private final ListMapper listMapper;
//	@Autowired
//	ModelMapper modelMapper;	
	
	@Override
    public ListeDTO saveIdFilm(ListeDTO l) {
        Liste liste = listMapper.listDtoToList(l); // Conversion DTO à Entity
        Liste savedListe = malisteRepository.save(liste);
        return listMapper.toListDto(savedListe); // Conversion Entity à DTO
    }
    
    @Override
    public ListeDTO getIdFilm(Long idFilm) {
        return malisteRepository.findByIdFilm(idFilm)
                .map(listMapper::toListDto) // Utilisez la méthode de conversion
                .orElse(null);
    }
    
    @Override
    public List<ListeDTO> getAllFilms() {
        return malisteRepository.findAll().stream()
                .map(listMapper::toListDto) // Utilisez la méthode de conversion
                .collect(Collectors.toList());
    }
	@Override
	public Optional<Liste> findByIdFilm(Long idFilm) {
	    return malisteRepository.findByIdFilm(idFilm);
	}
	@Override
	public void deleteFilmByIdFilm(Long idFilm) {
		Optional<Liste> liste = malisteRepository.findByIdFilm(idFilm);
	    if (liste.isPresent()) {
	        malisteRepository.delete(liste.get());
	    }
	}

	
//	@Override
//	public ListeDTO convertEntityToDto(Liste liste) {
//		
//		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);//afficher nomCat 
//		ListeDTO listeDTO = modelMapper.map(liste, ListeDTO.class);
//		return listeDTO;
//	}
//	
//	@Override
//	public Liste convertDtoToEntity(ListeDTO listeDto) {
//		
//		Liste liste = new Liste();
//		liste = modelMapper.map(listeDto,Liste.class);
//		return liste;
//		
//	}

}
