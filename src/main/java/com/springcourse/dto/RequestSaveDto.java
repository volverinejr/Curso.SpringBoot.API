package com.springcourse.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.springcourse.domain.Request;
import com.springcourse.domain.RequestStage;
import com.springcourse.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestSaveDto {

	@NotBlank
	private String subject;

	@NotBlank
	private String description;

	@NotNull
	private User owner;

	private List<RequestStage> stages = new ArrayList<RequestStage>();

	public Request trasnformToRequest() {
		Request request = new Request(null, this.subject, this.description, null, null, this.owner, this.stages, null);

		return request;
	}

}
