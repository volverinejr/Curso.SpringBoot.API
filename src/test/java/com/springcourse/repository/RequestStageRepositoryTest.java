package com.springcourse.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import com.springcourse.domain.Request;
import com.springcourse.domain.RequestStage;
import com.springcourse.domain.User;
import com.springcourse.domain.enums.RequestState;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class RequestStageRepositoryTest {
	
	@Autowired
	private RequestStageRepository _requestStageRepository;
	
	
	@Test
	@Order(1)
	public void saveTest() {
		Request request = new Request();
		request.setId(1L);

		User owner = new User();
		owner.setId(1L);
		
		RequestStage stage = new RequestStage(null, "Foi comprado um novo Dell", OffsetDateTime.now(), RequestState.CLOSED, request, owner);
		
		RequestStage createdStage = _requestStageRepository.save(stage);
		
		assertThat(createdStage.getId()).isEqualTo(1L);
	}
	
	
	@Test
	@Order(2)
	public void getByIdTest() {
		Optional<RequestStage> result = _requestStageRepository.findById(1L);
		RequestStage stage = result.get();
		
		assertThat(stage.getDescription()).isEqualTo("Foi comprado um novo Dell");
		
	}
	
	@Test
	@Order(3)
	public void listByRequestIdTest() {
		List<RequestStage> stages = _requestStageRepository.findAllByRequestId(1L);
		
		assertThat(stages.size()).isEqualTo(1);
	}
	
	
	

}
