package com.springcourse.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springcourse.domain.Request;
import com.springcourse.domain.User;
import com.springcourse.dto.UserLoginDto;
import com.springcourse.dto.UserSaveDto;
import com.springcourse.dto.UserUpdateDto;
import com.springcourse.dto.UserUpdateRoleDto;
import com.springcourse.model.PageModel;
import com.springcourse.model.PageRequestModel;
import com.springcourse.service.RequestService;
import com.springcourse.service.UserService;


@RestController
@RequestMapping(value = "v1/users")
public class UserController {

	@Autowired
	private UserService _userService;
	
	@Autowired
	private RequestService _requestService;
	
	

	@PostMapping
	public ResponseEntity<User> save(@RequestBody @Valid UserSaveDto userSaveDto) {
		User user = userSaveDto.trasnformToUser();

		User createdUser = _userService.save(user);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody @Valid UserUpdateDto userUpdateDto) {
		User user = userUpdateDto.trasnformToUser();
		
		user.setId(id);
		User updateUser = _userService.update(user);

		return ResponseEntity.ok(updateUser);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable Long id) {
		User user = _userService.getById(id);

		return ResponseEntity.ok(user);
	}

	@GetMapping
	public ResponseEntity<PageModel<User>> listAll(
			@RequestParam Map<String, String> params
			) {
		PageRequestModel prm = new PageRequestModel(params);
		
		PageModel<User> pm = _userService.listAllOnLazyMode(prm);

		return ResponseEntity.ok(pm);
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody @Valid UserLoginDto userDto) {
		User usuarioLogado = _userService.login(userDto.getEmail(), userDto.getPassword());
		
		return ResponseEntity.ok(usuarioLogado);				
	}
	
	@GetMapping("/{id}/requests")
	public ResponseEntity<PageModel<Request>> listAllRequestsByOwnerId(
			@PathVariable Long id,
			@RequestParam Map<String, String> params
			){
		PageRequestModel prm = new PageRequestModel(params);
		
		PageModel<Request> pm = _requestService.listAllByOwnerIdOnLazyMode(id, prm);
		
		return ResponseEntity.ok(pm);
	}
	

	@PatchMapping("/{id}/role")
	public ResponseEntity<?> updateRole(
			@PathVariable Long id,
			@RequestBody @Valid UserUpdateRoleDto userUpdateRoleDto) {
		User user = new User();
		user.setId(id);
		user.setRole(userUpdateRoleDto.getRole());
		
		_userService.updateRole(user);
			
		return ResponseEntity.ok().build();
	}

}
