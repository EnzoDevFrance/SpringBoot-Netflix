package com.projet.netflix.service;

import com.projet.netflix.dto.request.AuthenticationRequest;
import com.projet.netflix.dto.request.RegisterRequest;
import com.projet.netflix.dto.response.AuthenticationResponse;

public interface AuthenticationService {
	
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
