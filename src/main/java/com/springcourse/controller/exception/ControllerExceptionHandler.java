package com.springcourse.controller.exception;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springcourse.exception.NotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	//Recurso não existe
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex) {
		ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), OffsetDateTime.now() );
	
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	//Campo não existe na tabela 
	@ExceptionHandler(PropertyReferenceException .class)
	public ResponseEntity<ApiError> handlePropertyReferenceException(PropertyReferenceException ex) {
		//ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage(), OffsetDateTime.now() );
		//return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
		
		ArrayList<ErroCampo> erros = new ArrayList<ErroCampo>();

		ErroCampo erroCampo = new ErroCampo(null, ex.getMessage());
		
		erros.add(erroCampo);

		String defaultMessage = "Campos Inválidos";
		
		ApiErrorList apiErrorList = new ApiErrorList(HttpStatus.BAD_REQUEST.value(), defaultMessage, OffsetDateTime.now(), erros );
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorList);
	}
	
	
	//Violação de integridade 
	@ExceptionHandler(DataIntegrityViolationException .class)
	public ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		//ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), OffsetDateTime.now() );
		//return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		ArrayList<ErroCampo> erros = new ArrayList<ErroCampo>();
		
		ErroCampo erroCampo = new ErroCampo(null, "Violação de Integridade");
		
		erros.add(erroCampo);

		String defaultMessage = "Campos Inválidos";
		
		ApiErrorList apiErrorList = new ApiErrorList(HttpStatus.BAD_REQUEST.value(), defaultMessage, OffsetDateTime.now(), erros );
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorList);
	}	
	
	
	
	//Campos não passaram nas validações
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ArrayList<ErroCampo> erros = new ArrayList<ErroCampo>();
		
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String campo =  ((FieldError) error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			ErroCampo erroCampo = new ErroCampo(campo, mensagem);

			erros.add(erroCampo);
		});
		String defaultMessage = "Campos Inválidos";
		
		ApiErrorList apiErrorList = new ApiErrorList(HttpStatus.BAD_REQUEST.value(), defaultMessage, OffsetDateTime.now(), erros );
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorList);
	}
	
	
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ApiError> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
		String[] parts = ex.getMessage().split(":");
		String msg = parts[parts.length - 1].trim().toUpperCase();
		
		ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), msg, OffsetDateTime.now());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}	

}
