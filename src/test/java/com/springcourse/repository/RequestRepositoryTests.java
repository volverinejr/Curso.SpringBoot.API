package com.springcourse.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import com.springcourse.domain.Request;
import com.springcourse.domain.User;
import com.springcourse.domain.enums.RequestState;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class RequestRepositoryTests {
	
	@Autowired
	private RequestRepository _requestRepository;
	
	@Test
	@Order(1)
	public void saveTest() {
		User owner = new User();
		owner.setId(1L);
		
		Request request = new Request(null, "Novo notebook", "Coprar um novo note book cala", OffsetDateTime.now(), RequestState.OPEN, owner, null, null);
		
		Request createdRequest = _requestRepository.save(request);
		
		assertThat(createdRequest.getId()).isEqualTo(1L);
	}
	
	
	@Test
	@Order(2)
	public void updateTest() {
		User owner = new User();
		owner.setId(1L);
		
		Request request = new Request(1L, "Novo notebook DELL", "Comprar um DEL SSD", null, RequestState.OPEN, owner, null, null);
		
		Request createdRequest = _requestRepository.save(request);
		
		assertThat(createdRequest.getSubject()).isEqualTo("Novo notebook DELL");
	}
	
	@Test
	@Order(3)
	public void getByIdTest() {
		Optional<Request> result = _requestRepository.findById(1L);
		Request request = result.get();
		
		assertThat(request.getDescription()).isEqualTo("Comprar um DEL SSD");
	}
	
	
	@Test
	@Order(4)
	public void listTest() {
		List<Request> requests = _requestRepository.findAll();
		
		assertThat(requests.size()).isEqualTo(1);
	}
	
	
	@Test
	@Order(5)
	public void listByOwnerIdTest() {
		List<Request> requests = _requestRepository.findAllByOwnerId(1L);
		
		assertThat(requests.size()).isEqualTo(1);
	}
	
	
	@Test
	@Order(6)
	public void updateStateTest() {
		int linhasAfetadas = _requestRepository.updateState(1L, RequestState.IN_PROGRESS);
		
		assertThat(linhasAfetadas).isEqualTo(1);
	}

}
