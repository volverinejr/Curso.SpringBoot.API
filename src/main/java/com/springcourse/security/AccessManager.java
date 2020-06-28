package com.springcourse.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.springcourse.domain.Request;
import com.springcourse.domain.User;
import com.springcourse.exception.NotFoundException;
import com.springcourse.repository.UserRepository;
import com.springcourse.service.RequestService;

@Component("accessManager")
public class AccessManager {

	@Autowired
	private UserRepository _userRepository;

	@Autowired
	private RequestService _requestService;

	
	
	public boolean isOwner(Long id) {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Optional<User> result = _userRepository.findByEmail(email);

		User user = result.orElseThrow(() -> new NotFoundException("Nao existe User com email: " + email));

		return user.getId() == id;
	}

	public boolean isRequestOwner(Long id) {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Optional<User> result = _userRepository.findByEmail(email);

		User user = result.orElseThrow(() -> new NotFoundException("Nao existe User com email: " + email));

		Request request = _requestService.getById(id);

		return user.getId() == request.getOwner().getId();
	}
}
