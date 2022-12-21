package com.epam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.entity.UserDto;
import com.epam.exception.UserException;
import com.epam.filter.JwtRequestFilter;
import com.epam.service.AppUserDetailService;
import com.epam.service.UserService;
import com.epam.util.JwtUtil;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	JwtRequestFilter jwtRequestFilter;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	AppUserDetailService appUserDetailService;

	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<UserDto>> getAll() {
		return new ResponseEntity<List<UserDto>>(userService.getAll(), HttpStatus.OK);
	}
	

	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	@GetMapping("/{username}")
	public ResponseEntity<UserDto> get(@PathVariable String username) throws UserException {
		return new ResponseEntity<UserDto>(userService.get(username), HttpStatus.OK);
	}

	
	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	@GetMapping("/profile")
	public ResponseEntity<UserDto> getUserData() throws UserException {
		return new ResponseEntity<UserDto>(userService.get(jwtRequestFilter.getUsername1()), HttpStatus.OK);
	}

	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserDto dto) throws UserException {
		return new ResponseEntity<String>(userService.save(dto), HttpStatus.CREATED);
	}
	

	@GetMapping("/authenticate")
	public String createToken(@RequestBody UserDto dto) throws UserException {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

		} catch (BadCredentialsException e) {
			throw new UserException("Incorrect username and password");
		}
		final UserDetails userDetail = appUserDetailService.loadUserByUsername(dto.getUsername());
		return jwtUtil.generateToken(userDetail);
	}

}
