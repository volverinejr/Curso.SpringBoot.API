package com.springcourse.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springcourse.domain.Request;
import com.springcourse.domain.RequestFile;
import com.springcourse.model.PageModel;
import com.springcourse.model.PageRequestModel;
import com.springcourse.model.UploadedFileModel;
import com.springcourse.repository.RequestFileRepository;
import com.springcourse.service.s3.S3Service;

@Service
public class RequestFileService {

	@Autowired
	private RequestFileRepository _requestFileRepository;
	

	@Autowired
	private S3Service _s3Service;

	
	public PageModel<RequestFile> listAllByRequestIdOnLazyMode(Long requestId, PageRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();
		
		Page<RequestFile> page = _requestFileRepository.findAllByRequestId(requestId, pageable);
		
		PageModel<RequestFile> pm = new PageModel<>(page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
		
		return pm;
	}
	
	
	public List<RequestFile> upload(Long requestId, MultipartFile[] files){
		
		List<UploadedFileModel> uploadedFiles = _s3Service.upload(files);
		
		List<RequestFile> requestFiles = new ArrayList<RequestFile>();
		
		Request request = new Request();
		request.setId(requestId);
		
		uploadedFiles.forEach(elemento -> {
			RequestFile file = new RequestFile(null, elemento.getName(), elemento.getLocation(), request);
			
			requestFiles.add(file);
		});
		
		
		return _requestFileRepository.saveAll(requestFiles);
	}
	
}
