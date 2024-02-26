 package com.projet.netflix.service;

 import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;
import com.projet.netflix.entities.Session;
import org.springframework.security.authentication.AuthenticationManager;
 import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
 import org.springframework.security.crypto.password.PasswordEncoder;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;

import com.projet.netflix.dto.request.AuthenticationRequest;
import com.projet.netflix.dto.request.RegisterRequest;
import com.projet.netflix.dto.response.AuthenticationResponse;
import com.projet.netflix.entities.Role;
import com.projet.netflix.entities.TokenType;
import com.projet.netflix.entities.User;
import com.projet.netflix.repos.SessionRepository;
import com.projet.netflix.repos.UtilisateurRepository;



 @Service @Transactional
 @RequiredArgsConstructor
 public class AuthenticationServiceImpl implements AuthenticationService {

     private final PasswordEncoder passwordEncoder;
     private final JwtService jwtService;
     private final UtilisateurRepository userRepository;
     private final AuthenticationManager authenticationManager;
     private final RefreshTokenService refreshTokenService;
 	 private final SessionRepository sessionRepository;
     @Override
     public AuthenticationResponse register(RegisterRequest request) {
         var user = User.builder()
                 .firstName(request.getFirstname())
                 .lastName(request.getLastname())
                 .email(request.getEmail())
                 .password(passwordEncoder.encode(request.getPassword()))
                 .age(request.getAge())
                 .role(Role.ROLE_USER)//role(request.getRole())
                 .build();
         user = userRepository.save(user);
         
         // Création d'une session par défaut pour l'utilisateur
         Session defaultSession = new Session();
         defaultSession.setName(user.getFirstName());
         defaultSession.setUtilisateur(user);
         sessionRepository.save(defaultSession);
         
         
         var jwt = jwtService.generateToken(user);
         var refreshToken = refreshTokenService.createRefreshToken(user.getId());

         var role = user.getRole().name();
//                 .stream()
//                 .map(GrantedAuthority::getAuthority)
//                 .collect(Collectors.toList());

         return AuthenticationResponse.builder()
                 .accessToken(jwt)
                 .email(user.getEmail())
                 .id(user.getId())
                 .refreshToken(refreshToken.getToken())
                 .roles(role)
                 .tokenType( TokenType.BEARER.name())
                 .build();
     }

     @Override
     public AuthenticationResponse authenticate(AuthenticationRequest request) {
         authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

         var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
         var role = user.getRole().name();
//                 .stream()
//                 .map(SimpleGrantedAuthority::getAuthority)
//                 .toList();
         var jwt = jwtService.generateToken(user);
         var refreshToken = refreshTokenService.createRefreshToken(user.getId());
         return AuthenticationResponse.builder()
                 .accessToken(jwt)
                 .roles(role)//roles
                 .email(user.getEmail())
                 .id(user.getId())
                 .refreshToken(refreshToken.getToken())
                 .tokenType( TokenType.BEARER.name())
                 .build();
     }
 }