package com.springcourse.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.springcourse.domain.Request;
import com.springcourse.domain.RequestStage;
import com.springcourse.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {

	@NotBlank
	private String name;

	@Email
	private String email;

	@NotBlank
	@Size(min = 7, max = 10)
	private String password;

	private List<Request> requests = new ArrayList<Request>();
	private List<RequestStage> stages = new ArrayList<RequestStage>();

	public User trasnformToUser() {
		User user = new User(null, this.name, this.email, this.password, null, this.requests, this.stages);

		return user;
	}

}
