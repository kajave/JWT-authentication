package com.epam.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.entity.UserDto;
import com.epam.service.UserService;
import com.epam.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
class LoginControllerTest {

	@MockBean
	UserService userService;
	
	@InjectMocks
	UserController userController;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	JwtUtil jwtUtil;
	
	ModelMapper modelMapper = new ModelMapper();
	private static ObjectMapper mapper = new ObjectMapper();
	private String jwt;
	
	@BeforeEach
	void initialize() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ADMIN"));
		UserDetails userDetails = new org.springframework.security.core.userdetails.User("Ask", "12345", authorities);
		jwt = jwtUtil.generateToken(userDetails);
	}
	
	
	@Test
	void testGetAllUser() throws Exception {
		List<UserDto> asList = Arrays.asList(new UserDto());
		when(userService.get(anyString())).thenReturn(new UserDto());
		mockMvc.perform(get("/user/Ak").header("Authorization", "Bearer" + jwt)).andDo(print()).andExpect(status().isOk());
	}

}
