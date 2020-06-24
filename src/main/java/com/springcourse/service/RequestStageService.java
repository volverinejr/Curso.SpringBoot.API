package com.springcourse.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springcourse.domain.RequestStage;
import com.springcourse.domain.enums.RequestState;
import com.springcourse.exception.NotFoundException;
import com.springcourse.model.PageModel;
import com.springcourse.model.PageRequestModel;
import com.springcourse.repository.RequestRepository;
import com.springcourse.repository.RequestStageRepository;

@Service
public class RequestStageService {
	
	@Autowired
	private RequestStageRepository _requestStageRepository;
	
	@Autowired
	private RequestRepository _requestRepository;
	
	public RequestStage save(RequestStage stage) {
		stage.setRealizationDate( OffsetDateTime.now() );
		
		RequestStage createdStage = _requestStageRepository.save(stage);
		
		Long requestId = stage.getRequest().getId();
		RequestState state = stage.getState();
		
		_requestRepository.updateState(requestId, state);
		
		return createdStage;
	}
	
	public RequestStage getById(Long id) {
		Optional<RequestStage> result = _requestStageRepository.findById(id);

		return result.orElseThrow( ()-> new NotFoundException("Nao existe State com id: " + id));
	}
	
	public List<RequestStage> listAllByRequestId(Long requestId) {
		List<RequestStage> stages = _requestStageRepository.findAllByRequestId(requestId);
		
		return stages;
	}
	
	public PageModel<RequestStage> listAllByRequestIdOnLazyMode(Long requestId, PageRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();
		Page<RequestStage> page = _requestStageRepository.findAllByRequestId(requestId, pageable);
		
		PageModel<RequestStage> pm = new PageModel<>(page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
		
		return pm;
	}	
	

}
