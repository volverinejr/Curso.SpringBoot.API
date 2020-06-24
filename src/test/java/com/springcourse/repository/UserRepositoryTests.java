package com.springcourse.repository;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import com.springcourse.domain.User;
import com.springcourse.domain.enums.Role;



@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository userRepository; 

	
	
	@Test
	@Order(1)
	public void saveTest() {
		User user = new User(null, "miro", "miro@gmail.com", "123456", Role.ADMINISTRADOR, null, null);
		
		User createdUser = userRepository.save(user);
		
		assertThat(createdUser.getId()).isEqualTo(1L);
	}
	

	@Test
	@Order(2)
	public void updateTest() {
		User user = new User(1L, "miro Jr", "mirojr@gmail.com", "123456", Role.ADMINISTRADOR, null, null);
		
		User updateUser = userRepository.save(user);
		
		assertThat(updateUser.getName()).isEqualTo("miro Jr");
	}
	
	
	@Test
	@Order(3)
	public void getByIdTest() {
		Optional<User> result = userRepository.findById(1L);
		User user = result.get();
		
		assertThat(user.getEmail()).isEqualTo("mirojr@gmail.com");
	}
	
	
	@Test
	@Order(4)
	public void listTest() {
		List<User> users = userRepository.findAll();
		
		assertThat(users.size()).isEqualTo(1);
	}
	
	
	@Test
	@Order(5)
	public void loginTest() {
		Optional<User> result = userRepository.login("mirojr@gmail.com", "123456");
		User usuarioLogado = result.get();
		
		assertThat(usuarioLogado.getId()).isEqualTo(1L);
	}
	
	@Test
	@Order(6)
	public void updateRoleTest() {
		int linhasAfetadas = userRepository.updateRole(1L, Role.ADMINISTRADOR);
		
		assertThat(linhasAfetadas).isEqualTo(1);
	}
	
}
