package com.springcourse.controller.exception;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiErrorList extends ApiError{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ErroCampo> erros;
	
	public ApiErrorList(int code, String msg, OffsetDateTime date, List<ErroCampo> erros) {
		super(code, msg, date);
		
		this.erros = erros;
	}

}
