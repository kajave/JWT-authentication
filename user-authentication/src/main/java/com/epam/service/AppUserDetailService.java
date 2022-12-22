package com.epam.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.epam.entity.AuthGroup;
import com.epam.entity.User;
import com.epam.exception.UserException;
import com.epam.repository.AuthGroupRepo;
import com.epam.repository.UserRepository;

@Component
public class AppUserDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthGroupRepo authGroupRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
		User u = user.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		List<AuthGroup> roles = authGroupRepo.findByUsername(username);

		org.springframework.security.core.userdetails.User userDetail = new org.springframework.security.core.userdetails.User(
				u.getUsername(), u.getPassword(),
				roles.stream().map(r -> new SimpleGrantedAuthority(r.getAuthGroup())).toList());
		return userDetail;
	}

}
