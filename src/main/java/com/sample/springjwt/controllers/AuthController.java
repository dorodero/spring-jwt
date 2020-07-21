package com.sample.springjwt.controllers;

import com.sample.springjwt.paylord.request.LoginRequest;
import com.sample.springjwt.paylord.request.SignupRequest;
import com.sample.springjwt.paylord.response.JwtResponse;
import com.sample.springjwt.paylord.response.MessageResponse;
import com.sample.springjwt.security.jwt.JwtUtils;
import com.sample.springjwt.security.services.RegisterUserService;
import com.sample.springjwt.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RegisterUserService registerUserService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, RegisterUserService registerUserService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.registerUserService = registerUserService;
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        // デフォルトのログインフォームを使用して
        // JWT用のフィルタで認証後にResponseHeaderにJwt付与がよさそう
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        registerUserService.regist(signUpRequest);

        return ok(new MessageResponse("User registered successfully!"));
    }
}
