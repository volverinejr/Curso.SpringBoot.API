package com.springcourse.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springcourse.domain.RequestStage;
import com.springcourse.dto.RequestStageSaveDto;
import com.springcourse.service.RequestStageService;

@RestController
@RequestMapping("v1/request-stages")
public class RequestStageController {
	
	
	@Autowired
	private RequestStageService _stageService;
	
	
	@PostMapping
	public ResponseEntity<RequestStage> save(@RequestBody @Valid RequestStageSaveDto requestStageSaveDto) {
		RequestStage stage = requestStageSaveDto.trasnformToRequestStage();
		
		RequestStage createdStage = _stageService.save(stage);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(createdStage);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<RequestStage> getById(@PathVariable Long id){
		RequestStage stage = _stageService.getById(id);
		
		return ResponseEntity.ok(stage);
	}
	
}
