package com.springcourse.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springcourse.domain.Request;
import com.springcourse.domain.enums.RequestState;
import com.springcourse.exception.NotFoundException;
import com.springcourse.model.PageModel;
import com.springcourse.model.PageRequestModel;
import com.springcourse.repository.RequestRepository;

@Service
public class RequestService {
	
	@Autowired
	private RequestRepository _requestRepository;
	

	public Request save(Request request) {
		request.setState(RequestState.OPEN);
		request.setCriationDate( OffsetDateTime.now() );
		
		Request createdRequest = _requestRepository.save(request);
		
		return createdRequest;
	}
	
	public Request update(Request request) {
		Request updateRequest = _requestRepository.save(request);
		
		return updateRequest;
	}


	public Request getById(Long id) {
		Optional<Request> result = _requestRepository.findById(id);
		
		return result.orElseThrow( ()-> new NotFoundException("Nao existe Request com id: " + id));
	}
	
	
	public List<Request> listAll() {
		List<Request> requests = _requestRepository.findAll();
		
		return requests;
	}
	
	
	public PageModel<Request> listAllOnLazyMode(PageRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();
		
		Page<Request> page = _requestRepository.findAll(pageable);
		
		PageModel<Request> pm = new PageModel<>(page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
		
		return pm;
	}
	
	
	
	
	public List<Request> listAllByOwnerId(Long owner) {
		List<Request> requests = _requestRepository.findAllByOwnerId(owner);
		
		return requests;
	}
	
	
	public PageModel<Request> listAllByOwnerIdOnLazyMode(Long owner, PageRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();
				
		Page<Request> page = _requestRepository.findAllByOwnerId(owner, pageable);
		
		PageModel<Request> pm = new PageModel<>(page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
		
		return pm;
	}
		
	
}
