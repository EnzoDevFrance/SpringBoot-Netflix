package com.projet.netflix.mappers;

import org.mapstruct.Mapper;

import com.projet.netflix.dto.ListeDTO;
import com.projet.netflix.entities.Liste;

@Mapper(componentModel = "spring") 
public interface ListMapper {

    ListeDTO toListDto(Liste liste);
    Liste listDtoToList(ListeDTO listeDto);
	
}
