package com.example.cms.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cms.DAO.UserRequestDTO;
import com.example.cms.DAO.UserResponse;
import com.example.cms.entity.User;
import com.example.cms.exception.UserAlreadyExistByEmailException;
import com.example.cms.repositary.UserRepository;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private ResponseStructure<UserResponse> responseStructure;
	private UserResponse userResponse;
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, ResponseStructure<UserResponse> responseStrecture , UserResponse userResponse ,PasswordEncoder passwordEncoder ) {

		this.userRepository = userRepository;
		this.responseStructure = responseStrecture;
		this.userResponse=userResponse;
		this.passwordEncoder = passwordEncoder;
	}

	public ResponseEntity<ResponseStructure<UserResponse>> registerUser(UserRequestDTO userRequestDTO){
		if(userRepository.existsByEmail(userRequestDTO.getEmail()))
			throw new UserAlreadyExistByEmailException("Failed To register user");

		User user =userRepository.save(mapToUserEntity(userRequestDTO , new User()));


		return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value()).setMessage("User Data Sucessfully").setData(mapToUserResponse(user)));
	}

	private User mapToUserEntity(UserRequestDTO userRequestDTO, User user ) {
		user.setUserName(userRequestDTO.getUserName());
		user.setEmail(userRequestDTO.getEmail());
		user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
		return user;

	}

	private UserResponse mapToUserResponse(User user) {
		return   userResponse.setUserName(user.getUserName())
				.setEmail(user.getEmail())
				.setUserId(user.getUserId());
				
	}

}
