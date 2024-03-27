package com.example.cms.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cms.repositary.UserRepository;

@Service
public class CostumUserDetailService implements UserDetailsService {
	
	private UserRepository userRepository;
	public CostumUserDetailService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username).map(user -> 
		new CostumUserDetails(user)).orElseThrow(() ->
		new UsernameNotFoundException("User Name Not Found Please Check"));
	}

}
