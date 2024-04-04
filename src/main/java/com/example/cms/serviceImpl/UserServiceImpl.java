package com.example.cms.serviceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cms.DAO.UserRequestDTO;
import com.example.cms.DAO.UserResponse;
import com.example.cms.entity.User;
import com.example.cms.exception.UserAlreadyExistByEmailException;
import com.example.cms.exception.UserNotFoundById;
import com.example.cms.repositary.UserRepository;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private ResponseStructure<UserResponse> responseStructure;
	private UserResponse userResponse;
	private PasswordEncoder passwordEncoder;
	private ResponseStructure<String> responseStructureString;

	public UserServiceImpl(UserRepository userRepository, ResponseStructure<UserResponse> responseStrecture , UserResponse userResponse ,PasswordEncoder passwordEncoder , ResponseStructure<String> responseStructureString ) {

		this.userRepository = userRepository;
		this.responseStructure = responseStrecture;
		this.userResponse=userResponse;
		this.passwordEncoder = passwordEncoder;
		this.responseStructureString = responseStructureString;
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
				.setUserId(user.getUserId())
				.setCreatedAt(user.getCreatedAt())
				.setLastModifiedAt(user.getLastModifiedAt());

	}

	@Override
	public ResponseEntity<ResponseStructure<String>> softDelete(int userId) {
		

		return userRepository.findById(userId).map(user ->{
			 user.setDeleteUser(true);
			userRepository.save(user);
			
			return  ResponseEntity.ok(responseStructureString.setStatusCode(HttpStatus.OK.value()).setMessage("User Is Deactivated Sucessfully")
					.setData("Your Account is Deactivated Please Activated"));
		}).orElseThrow(() -> new UserNotFoundById("Your Id is Not Found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(int userId) {
		
		
		
		return userRepository.findById(userId).map(user -> {
			if(user.isDeleteUser()==false) 
			responseStructure.setStatusCode(HttpStatus.FOUND.value())
			.setMessage("User Object Found Sucessfully")
			.setData(mapToUserResponse(user));

			return ResponseEntity.ok(responseStructure);
			
		}).orElseThrow(() -> new UserNotFoundById("User Id is Not Foud In DataBase"));
		
		
	}
}
