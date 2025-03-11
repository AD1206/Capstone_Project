package com.skillbridge.skillbridge.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillbridge.skillbridge.exception.JobPortalException;
import com.skillbridge.skillbridge.jwt.AuthenticationRequest;
import com.skillbridge.skillbridge.jwt.AuthenticationResponse;
import com.skillbridge.skillbridge.jwt.JwtHelper;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthAPI {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@PostMapping("/login")
	public ResponseEntity<?>createAuthenticationToken(@RequestBody AuthenticationRequest request) throws JobPortalException{
		try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new JobPortalException("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String jwt = jwtHelper.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
}
