package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.DAO.UserRequestDTO;
import com.example.cms.DAO.UserResponse;
import com.example.cms.service.UserService;
import com.example.cms.utility.ErrorStructure;
import com.example.cms.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {

		this.userService = userService;
	}

	@Operation(description = "this endpoint is used to register the Users" , responses = {
			@ApiResponse(responseCode = "200", description = "User Registered Sucessfully"),
			@ApiResponse(responseCode = "400" , description = "Invalid Input" , content = @Content(schema = @Schema(implementation = ErrorStructure.class ))),
			@ApiResponse(responseCode = "404" , description = "Invalid Input" , content = @Content(schema = @Schema(implementation = ErrorStructure.class )))})
	

	@PostMapping("/users/register")
	public ResponseEntity<ResponseStructure<UserResponse>> save(@RequestBody @Valid UserRequestDTO userRequestDto){
		return userService.registerUser(userRequestDto);
	}

	@GetMapping("/test")
	public String test() {
		return "Hellow From CMS";
	}
	@Operation(description = "this endpoint is used to SoftDeleting the Users" , responses = {
			@ApiResponse(responseCode = "200", description = "User is deactivated"),
			@ApiResponse(responseCode = "404" , description = "Invalid user Id" , content = @Content(schema = @Schema(implementation = ErrorStructure.class )))})
	@DeleteMapping("/users/{userId}")
	private ResponseEntity<ResponseStructure<String>> softDelete(@PathVariable int userId){
		return userService.softDelete(userId);
	}
	@Operation(description = "this endpoint is used to Find the Users" , responses = {
			@ApiResponse(responseCode = "200", description = "User is deactivated"),
			@ApiResponse(responseCode = "404" , description = "Invalid user Id" , content = @Content(schema = @Schema(implementation = ErrorStructure.class )))})
	
	@GetMapping("users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(@PathVariable int userId) {
		return userService.findUserById(userId);
	}
}
