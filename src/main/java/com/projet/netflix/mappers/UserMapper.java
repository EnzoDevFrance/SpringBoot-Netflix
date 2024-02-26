package com.projet.netflix.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.projet.netflix.dto.UserDto;
import com.projet.netflix.dto.UtilisateurDTO;
import com.projet.netflix.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    // Ajoutez cette méthode pour convertir UtilisateurDTO en User.
    User utilisateurDtoToUser(UtilisateurDTO utilisateurDTO);

    // Ajoutez cette méthode pour convertir User en UtilisateurDTO.
    UtilisateurDTO userToUtilisateurDTO(User user);
    
}