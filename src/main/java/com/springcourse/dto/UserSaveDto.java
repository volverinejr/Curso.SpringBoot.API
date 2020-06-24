package com.springcourse.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.springcourse.domain.Request;
import com.springcourse.domain.RequestStage;
import com.springcourse.domain.User;
import com.springcourse.domain.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSaveDto {

	@NotBlank
	private String name;

	@Email
	private String email;

	@NotBlank
	@Size(min = 7, max = 10)
	private String password;

	@NotNull
	private Role role;

	private List<Request> requests = new ArrayList<Request>();
	private List<RequestStage> stages = new ArrayList<RequestStage>();

	public User trasnformToUser() {
		User user = new User(null, this.name, this.email, this.password, this.role, this.requests, this.stages);

		return user;
	}

}
