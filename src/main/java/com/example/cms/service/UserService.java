package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.DAO.UserRequestDTO;
import com.example.cms.DAO.UserResponse;
import com.example.cms.utility.ResponseStructure;

public interface UserService {

	ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequestDTO userRequestDto);

	ResponseEntity<ResponseStructure<String>> softDelete(int userId);

	ResponseEntity<ResponseStructure<UserResponse>> findUserById(int userId);

}
