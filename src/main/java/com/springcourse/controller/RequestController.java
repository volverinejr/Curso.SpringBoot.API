package com.springcourse.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springcourse.domain.Request;
import com.springcourse.domain.RequestFile;
import com.springcourse.domain.RequestStage;
import com.springcourse.dto.RequestSaveDto;
import com.springcourse.dto.RequestUpdateDto;
import com.springcourse.model.PageModel;
import com.springcourse.model.PageRequestModel;
import com.springcourse.service.RequestFileService;
import com.springcourse.service.RequestService;
import com.springcourse.service.RequestStageService;

@RestController
@RequestMapping("v1/requests")
public class RequestController {

	@Autowired
	private RequestService _requestService;

	@Autowired
	private RequestStageService _stageService;

	@Autowired
	private RequestFileService _fileService;

	@PostMapping
	public ResponseEntity<Request> save(@RequestBody @Valid RequestSaveDto requestSaveDto) {
		Request request = requestSaveDto.trasnformToRequest();

		Request createdRequest = _requestService.save(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Request> update(@PathVariable Long id,
			@RequestBody @Valid RequestUpdateDto requestUpdateDto) {
		Request request = requestUpdateDto.trasnformToRequest();

		request.setId(id);
		Request updateRequest = _requestService.update(request);

		return ResponseEntity.ok(updateRequest);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Request> getById(@PathVariable Long id) {
		Request request = _requestService.getById(id);

		return ResponseEntity.ok(request);
	}

	@GetMapping
	public ResponseEntity<PageModel<Request>> listAll(@RequestParam Map<String, String> params) {
		PageRequestModel prm = new PageRequestModel(params);

		PageModel<Request> pm = _requestService.listAllOnLazyMode(prm);

		return ResponseEntity.ok(pm);
	}

	@GetMapping("/{id}/request-stages")
	public ResponseEntity<PageModel<RequestStage>> listAllStagesById(@PathVariable Long id,
			@RequestParam Map<String, String> params) {
		PageRequestModel prm = new PageRequestModel(params);

		PageModel<RequestStage> pm = _stageService.listAllByRequestIdOnLazyMode(id, prm);

		return ResponseEntity.ok(pm);
	}

	@GetMapping("/{id}/request-files")
	public ResponseEntity<PageModel<RequestFile>> listAllFilesById(@PathVariable Long id,
			@RequestParam Map<String, String> params) {
		PageRequestModel prm = new PageRequestModel(params);

		PageModel<RequestFile> pm = _fileService.listAllByRequestIdOnLazyMode(id, prm);

		return ResponseEntity.ok(pm);
	}
	
	
	
	@PostMapping("/{id}/files")
	public ResponseEntity<List<RequestFile>> upload(
			@PathVariable Long id,
			@RequestParam MultipartFile[] files
			){
		List<RequestFile> requestFiles = _fileService.upload(id, files);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(requestFiles);
	}
	

}
