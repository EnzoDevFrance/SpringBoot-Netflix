package com.projet.netflix.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projet.netflix.dto.UtilisateurDTO;
import com.projet.netflix.entities.User;
import com.projet.netflix.mappers.UserMapper;
import com.projet.netflix.repos.SessionRepository;
import com.projet.netflix.repos.UtilisateurRepository;

import lombok.RequiredArgsConstructor;

import com.projet.netflix.dto.SessionDTO;
import com.projet.netflix.dto.UserDto;
import com.projet.netflix.entities.Role;
import com.projet.netflix.entities.Session;

@RequiredArgsConstructor
//@Transactional
@Service
public class UtilisateurServiceImpl implements UtilisateurService{
	

	private final UtilisateurRepository utilisateurRepository;
	
	private final SessionRepository sessionRepository;
	
	//private final ModelMapper modelMapper;
	
	private final PasswordEncoder passwordEncoder;
	
	private final UtilisateurRepository userRepository;

    private final UserMapper userMapper;


	
	@Override
	public UserDto getUtilisateur(Long id) {
		User user = utilisateurRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));  // Ajoutez une gestion appropriée pour l'utilisateur non trouvé.
	    return userMapper.toUserDto(user);  // Utilisez UserMapper pour convertir
	}
    

    @Override
    public UtilisateurDTO updateUtilisateur(UtilisateurDTO utilisateurDTO) {
        // Convertissez le DTO en entité
        User utilisateurToUpdate = userMapper.utilisateurDtoToUser(utilisateurDTO);

        // Mettez à jour l'utilisateur en base de données
        User updatedUtilisateur = utilisateurRepository.save(utilisateurToUpdate);

        // Convertissez l'entité mise à jour en DTO pour le retour
        return userMapper.userToUtilisateurDTO(updatedUtilisateur);
    }
	
	public boolean changePassword(Long userId, String oldPassword, String newPassword) {
	    User user = utilisateurRepository.findById(userId).orElse(null);
	    if (user == null) {
	        throw new UsernameNotFoundException("Utilisateur non trouvé avec l'ID: " + userId);
	    }
	    // Vérifiez que les mots de passe ne sont pas null
	    if (oldPassword == null || newPassword == null) {
	        throw new IllegalArgumentException("Les mots de passe ne peuvent pas être null");
	    }
	    // Vérifiez que l'ancien mot de passe correspond
	    if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
	        return false; // L'ancien mot de passe ne correspond pas
	    }
	    // Encodez et mettez à jour le nouveau mot de passe
	    user.setPassword(passwordEncoder.encode(newPassword));
	    utilisateurRepository.save(user);
	    return true;
	}
	
	
	
	@Override
	public void deleteUtilisateur(User u) {
		utilisateurRepository.delete(u);
	}
	@Override
	public void deleteUtilisateurById(Long id) {
		 utilisateurRepository.deleteById(id);
	}

	@Override
	public List<UserDto> getAllUtilisateurs() {
	    return utilisateurRepository.findAll().stream()
	            .map(userMapper::toUserDto)  // Utilisez UserMapper pour convertir
	            .collect(Collectors.toList());

	}
	//java fonctional programming = créer un code performant et concis
	@Override
	public List<User> findByNomUtilisateur(String nom) {
		// TODO Auto-generated method stub 
		return utilisateurRepository.findByLastName(nom);
	}
	@Override
	public List<User> findByNomUtilisateurContains(String nom) {
		return utilisateurRepository.findByLastNameContains(nom);
	}
	@Override
	public List<User> findByOrderByNomUtilisateurAsc() {
		// TODO Auto-generated method stub
		return utilisateurRepository.findByOrderByLastNameAsc();
	}
	
//	@Override
//	public UtilisateurDTO convertEntityToDto(User utilisateur) {
//		
//		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);//afficher nomCat 
//		UtilisateurDTO utilisateurDTO = modelMapper.map(utilisateur, UtilisateurDTO.class);
//		
//		return utilisateurDTO;
//		
//	}
//	
//	@Override
//	public User convertDtoToEntity(UtilisateurDTO utilisateurDto) {
//		
//		User utilisateur = new User();
//		utilisateur = modelMapper.map(utilisateurDto,User.class);
//		return utilisateur;
//		
//	}
	
//	@Override
//    public UtilisateurDTO saveUtilisateur(UtilisateurDTO utilisateurDTO) {
//        // Convertir le DTO en entité
//        User utilisateur = userMapper.utilisateurDtoToUser(utilisateurDTO);
//
//        // Vérifier si l'email est déjà utilisé
//        if (utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
//            throw new RuntimeException("Email déjà utilisé!");
//        }
//
//        // Modifier le mot de passe
//        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
//
//        // Attribuer un rôle par défaut à l'utilisateur
//        utilisateur.setRole(Role.ROLE_USER);
//
//        // Sauvegarder l'utilisateur
//        User savedUtilisateur = utilisateurRepository.save(utilisateur);
//
//        // Créer une nouvelle instance de Session
//        Session newSession = new Session();
//        newSession.setName(savedUtilisateur.getFirstName());
//        newSession.setUtilisateur(savedUtilisateur);
//        sessionRepository.save(newSession);
//
//        // Convertir l'entité utilisateur en DTO pour le retour
//        return userMapper.userToUtilisateurDTO(savedUtilisateur);
//    }
}
