package com.epam.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.epam.entity.AuthGroup;
import com.epam.entity.User;
import com.epam.entity.UserDto;
import com.epam.exception.UserException;
import com.epam.repository.AuthGroupRepo;
import com.epam.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthGroupRepo authGroupRepo;
	
	@Autowired
	ModelMapper mapper;
	
	public String save(UserDto dto) throws UserException {
		User user = mapper.map(dto, User.class);
		try {
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));
			authGroupRepo.save(new AuthGroup(dto.getUsername(), "ADMIN"));
			userRepository.save(user);
		} catch (RuntimeException e) {
			throw new UserException("User not added ssuccesfully");
		}
		
		return "User added succesfully";
	}
	
	
	public List<UserDto> getAll(){
		List<User> users = userRepository.findAll();
		List<UserDto> userDto = users.stream().map(u->mapper.map(u, UserDto.class)).toList();
		return userDto;
	}
	
	
	public UserDto get(String username) throws UserException {
		Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsername(username));
		User user=optionalUser.orElseThrow(()->new UserException("Username not found"));
		UserDto userDto = mapper.map(user, UserDto.class);
		return userDto;
	}

}
