package com.springcourse.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springcourse.domain.User;
import com.springcourse.exception.NotFoundException;
import com.springcourse.model.PageModel;
import com.springcourse.model.PageRequestModel;
import com.springcourse.repository.UserRepository;
import com.springcourse.service.util.HashUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository _userRepository;

	public User save(User user) {
		// Encriptando a senha
		String hash = HashUtil.getSecureHash(user.getPassword());
		user.setPassword(hash);

		User createdUser = _userRepository.save(user);
		return createdUser;
	}

	@CacheEvict(value = "UserGetByIdCache", allEntries = true)
	public User update(User user) {
		// Encriptando a senha
		String hash = HashUtil.getSecureHash(user.getPassword());
		user.setPassword(hash);

		User updateUser = _userRepository.save(user);
		return updateUser;
	}

	@Cacheable("UserGetByIdCache")
	public User getById(Long id) {
		Optional<User> result = _userRepository.findById(id);

		return result.orElseThrow(() -> new NotFoundException("Nao existe User com id: " + id));
	}

	public PageModel<User> listAllOnLazyMode(PageRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();
		Page<User> page = _userRepository.findAll(pageable);

		PageModel<User> pm = new PageModel<>(page.getTotalElements(), page.getSize(), page.getTotalPages(),
				page.getContent());

		return pm;
	}

	public User login(String email, String password) {
		// Encriptando a senha
		String hash = HashUtil.getSecureHash(password);

		Optional<User> result = _userRepository.login(email, hash);

		return result.orElseThrow(() -> new NotFoundException("Login inválido"));
	}

	public int updateRole(User user) {
		return _userRepository.updateRole(user.getId(), user.getRole());
	}

}
