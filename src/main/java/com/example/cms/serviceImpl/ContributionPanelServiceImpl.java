package com.example.cms.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.DAO.BlogResponse;
import com.example.cms.DAO.ContributionPanelResponse;
import com.example.cms.DAO.UserResponse;
import com.example.cms.entity.Blog;
import com.example.cms.entity.ContributionPanel;
import com.example.cms.entity.User;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.PanelIdAlreadySetException;
import com.example.cms.exception.PannelNotFoundByIdException;
import com.example.cms.exception.UserHavingPanelException;
import com.example.cms.exception.UserIdIsNotFound;
import com.example.cms.exception.UserNotFoundById;
import com.example.cms.repositary.BlogRepository;
import com.example.cms.repositary.ContributionPanelRepository;
import com.example.cms.repositary.UserRepository;
import com.example.cms.service.ContributionPanelService;
import com.example.cms.utility.ResponseStructure;
@Service
public class ContributionPanelServiceImpl implements ContributionPanelService {

	private ContributionPanelRepository contributionRepository;
	private ResponseStructure<ContributionPanelResponse> responseStrecture;
	private UserRepository userRepository;
	private BlogRepository blogRepository;
	private ContributionPanelResponse contributionPanelResponse;
	private UserResponse userResponse;

	public ContributionPanelServiceImpl(ContributionPanelRepository contributionRepository,
			ResponseStructure<ContributionPanelResponse> responseStrecture, UserRepository userRepository,
			BlogRepository blogRepository, ContributionPanelResponse contributionPanelResponse,
			UserResponse userResponse) {
		super();
		this.contributionRepository = contributionRepository;
		this.responseStrecture = responseStrecture;
		this.userRepository = userRepository;
		this.blogRepository = blogRepository;
		this.contributionPanelResponse = contributionPanelResponse;
		this.userResponse = userResponse;
	}




	private ContributionPanelResponse mapToPanelResponse(ContributionPanel panel) {
		
		List<User> users = panel.getUsers();
		ContributionPanelResponse panels = new ContributionPanelResponse();
		ArrayList<UserResponse> list = new ArrayList<>();
		for (User user : users) {
			 userResponse.setUserId(user.getUserId()).setUserName(user.getUserName()).setEmail(user.getEmail()).setCreatedAt(user.getCreatedAt()).setLastModifiedAt(user.getLastModifiedAt());
			 list.add(userResponse);
		}
		 contributionPanelResponse.setPanelId(panel.getPanelId());
		 contributionPanelResponse.setUsers(list);	
		 return panels;
	} 
	
	
	
	
	@Override
	public ResponseEntity<ResponseStructure<ContributionPanelResponse>> addUserToContributionPanel( int userId, int panelId) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email).map(owner ->{
			return contributionRepository.findById(panelId).map(panel ->{
				if(!blogRepository.existsByUserAndContributionPanel(owner , panel)) 
					throw new IllegalAccessRequestException("Failed To Add Contributior");

				return userRepository.findById(userId).map(contributor ->{
					if(panel.getUsers().contains(contributor)==true)
						throw new UserHavingPanelException("Entered user already having same panel please add another user");
					panel.getUsers().add(contributor);
					contributionRepository.save(panel);

					return ResponseEntity.ok(responseStrecture.setStatusCode(HttpStatus.OK.value()).setMessage("Contributor Set Sucessfully").setData(mapToPanelResponse(panel)));
				}).orElseThrow(()-> new UserNotFoundById("Entered User Id is Not Found "));
			}).orElseThrow(()-> new PannelNotFoundByIdException("Entered panel Id is not Found"));
		}).get();

	}

	@Override
	public ResponseEntity<ResponseStructure<ContributionPanelResponse>> removeUserFromContributionPanel(int userId, int panelId) {
	    String email = SecurityContextHolder.getContext().getAuthentication().getName();
	    return userRepository.findByEmail(email).map(owner ->{
	        return contributionRepository.findById(panelId).map(panel ->{
	            if(!blogRepository.existsByUserAndContributionPanel(owner , panel)) 
	                throw new IllegalAccessRequestException("Faisled To Remove Contributor");

	            return userRepository.findById(userId).map(contributor ->{
	                if (panel.getUsers().remove(contributor)) {
	                    contributionRepository.save(panel);
	                    return ResponseEntity.ok(responseStrecture.setStatusCode(HttpStatus.OK.value()).setMessage("Contributor Removed Successfully").setData(mapToPanelResponse(panel)));
	                } else {
	                    throw new UserIdIsNotFound("Entered User Id is Not Found in the Contribution Panel");
	                }
	            }).orElseThrow(()-> new UserNotFoundById("Entered User Id is Not Found "));
	        }).orElseThrow(()-> new PannelNotFoundByIdException("Entered panel Id is not Found"));
	    }).get();
	}
}
