package com.epam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.entity.AuthGroup;

public interface AuthGroupRepo extends JpaRepository<AuthGroup, Integer>{

	List<AuthGroup> findByUsername(String username);

}
