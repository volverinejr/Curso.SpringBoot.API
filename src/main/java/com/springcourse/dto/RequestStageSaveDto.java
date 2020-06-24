package com.springcourse.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.springcourse.domain.Request;
import com.springcourse.domain.RequestStage;
import com.springcourse.domain.User;
import com.springcourse.domain.enums.RequestState;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestStageSaveDto {

	@NotBlank
	private String description;

	@NotNull
	private RequestState state;

	@NotNull
	private Request request;

	@NotNull
	private User owner;

	public RequestStage trasnformToRequestStage() {
		RequestStage requestStage = new RequestStage(null, this.description, null, this.state, this.request,
				this.owner);

		return requestStage;
	}

}
