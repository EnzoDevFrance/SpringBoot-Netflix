package com.projet.netflix.restcontrollers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import com.projet.netflix.dto.request.AuthenticationRequest;
import com.projet.netflix.dto.request.RefreshTokenRequest;
import com.projet.netflix.dto.request.RegisterRequest;
import com.projet.netflix.dto.response.AuthenticationResponse;
import com.projet.netflix.dto.response.RefreshTokenResponse;
import com.projet.netflix.service.AuthenticationService;
import com.projet.netflix.service.JwtService;
import com.projet.netflix.service.RefreshTokenService;



@Tag(name = "Authentication", description = "The Authentication API. Contains operations like login, logout, refresh-token etc.")
@RestController
@RequestMapping("/api")//"/api/v1/auth"
@SecurityRequirements() /*
This API won't have any security requirements. Therefore, we need to override the default security requirement configuration
with @SecurityRequirements()
*/
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.register(request);
        ResponseCookie jwtCookie = jwtService.generateJwtCookie(authenticationResponse.getAccessToken());
        ResponseCookie refreshTokenCookie = refreshTokenService.generateRefreshTokenCookie(authenticationResponse.getRefreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString())
                .body(authenticationResponse);
    }

    @PostMapping("/authenticate")
    @Operation(
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401",
                            content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}
                    )
            }
    )
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);

        // Création des cookies avec setSecure(false)
        ResponseCookie jwtCookie = ResponseCookie.from("jwt-cookie", authenticationResponse.getAccessToken()) // Nom du cookie JWT
                .path("/")
                .httpOnly(true)
                .secure(false) // setSecure(false)
                .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refresh-jwt-cookie", authenticationResponse.getRefreshToken()) // Nom du cookie de token de rafraîchissement
                .path("/")
                .httpOnly(true)
                .secure(false) // setSecure(false)
                .build();

        // Affichage des informations sur les cookies
        System.out.println("JWT Cookie: " + jwtCookie.toString());
        System.out.println("Refresh Token Cookie: " + refreshTokenCookie.toString());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(authenticationResponse);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(refreshTokenService.generateNewToken(request));
    }

    @PostMapping("/refresh-token-cookie")
    public ResponseEntity<Void> refreshTokenCookie(HttpServletRequest request) {
        String refreshToken = refreshTokenService.getRefreshTokenFromCookies(request);
        RefreshTokenResponse refreshTokenResponse = refreshTokenService
                .generateNewToken(new RefreshTokenRequest(refreshToken));
        ResponseCookie NewJwtCookie = jwtService.generateJwtCookie(refreshTokenResponse.getAccessToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, NewJwtCookie.toString())
                .build();
    }
    @GetMapping("/info")
    public Authentication getAuthentication(@RequestBody AuthenticationRequest request){
        return     authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request){
        String refreshToken = refreshTokenService.getRefreshTokenFromCookies(request);
        if(refreshToken != null) {
           refreshTokenService.deleteByToken(refreshToken);
        }
        ResponseCookie jwtCookie = jwtService.getCleanJwtCookie();
        ResponseCookie refreshTokenCookie = refreshTokenService.getCleanRefreshTokenCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString())
                .build();

    }
    @GetMapping("/isAuthenticated")
    public ResponseEntity<Boolean> isAuthenticated(HttpServletRequest request) {
        String jwt = jwtService.getJwtFromCookies(request);
        System.out.println("JWT reçu: " + jwt); // Affiche le JWT reçu
        
        if (jwt != null) {
            try {
                // Utilise la logique existante pour extraire des informations du token
                // et/ou pour vérifier sa validité.
                String username = jwtService.extractUserName(jwt);
                System.out.println("Nom d'utilisateur extrait du JWT: " + username); // Affiche le nom d'utilisateur extrait du JWT
                
                if (username != null) {
                    System.out.println("Utilisateur authentifié: " + username); // Confirme que l'utilisateur est authentifié
                    return ResponseEntity.ok(true);
                }
            } catch (Exception e) {
                System.out.println("Erreur lors de la vérification du JWT: " + e.getMessage()); // Log l'erreur si le token est invalide
            }
        } else {
            System.out.println("Aucun JWT trouvé dans les cookies"); // Indique qu'aucun JWT n'a été trouvé
        }
        return ResponseEntity.ok(false);
    }

    
    
//    @GetMapping("/isAuthenticated")
//    public ResponseEntity<Boolean> isAuthenticated(HttpServletRequest request) {
//        // Spring Security devrait automatiquement remplir l'objet SecurityContextHolder
//        // avec les détails d'authentification si un cookie de session valide est présent.
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        boolean isAuthenticated = (authentication != null && !(authentication instanceof AnonymousAuthenticationToken));
//        return ResponseEntity.ok(isAuthenticated);
//    }
}
