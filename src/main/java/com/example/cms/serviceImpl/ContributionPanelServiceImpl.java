package com.example.cms.serviceImpl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.DAO.ContributionPanelResponse;
import com.example.cms.entity.ContributionPanel;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.PanelIdAlreadySetException;
import com.example.cms.exception.PannelNotFoundByIdException;
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
	private ResponseStructure<ContributionPanel> responseStrecture;
	private UserRepository userRepository;
	private BlogRepository blogRepository;

	public ContributionPanelServiceImpl(ContributionPanelRepository contributionRepository,
			ResponseStructure<ContributionPanel> responseStrecture, UserRepository userRepository,
			BlogRepository blogRepository) {
		super();
		this.contributionRepository = contributionRepository;
		this.responseStrecture = responseStrecture;
		this.userRepository = userRepository;
		this.blogRepository = blogRepository;
	}

	@Override
	public ResponseEntity<ResponseStructure<ContributionPanel>> addUserToContributionPanel( int userId, int panelId) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email).map(owner ->{
			return contributionRepository.findById(panelId).map(panel ->{
				if(!blogRepository.existsByUserAndContributionPanel(owner , panel)) 
					throw new IllegalAccessRequestException("Failed To Add Contributior");
//
//				Optional<ContributionPanel> existingPanel = contributionRepository.findById(panelId);
//
////				if(existingPanel.isPresent()) {
////					ContributionPanel exe = existingPanel.get();
////						
////					if(existingPanel.isPresent()==exe.getUsers().equals(owner)) {
////						throw new PanelIdAlreadySetException("Entered Panel ID is Already Seted This User please Choose Another user");
////						
//					}
//					if(existingPanel.isPresent()!=existingPanel.get().getUsers().equals(owner)) {
//						throw new PanelIdAlreadySetException("Entered Panel ID is Already Seted This User please Choose Another user");
//						
//				}

				return userRepository.findById(userId).map(contributor ->{
					panel.getUsers().add(contributor);
					contributionRepository.save(panel);

					return ResponseEntity.ok(responseStrecture.setStatusCode(HttpStatus.OK.value()).setMessage("Contributor Set Sucessfully").setData(panel));
				}).orElseThrow(()-> new UserNotFoundById("Entered User Id is Not Found "));
			}).orElseThrow(()-> new PannelNotFoundByIdException("Entered panel Id is not Found"));
		}).get();

	}

	@Override
	public ResponseEntity<ResponseStructure<ContributionPanel>> removeUserFromContributionPanel(int userId, int panelId) {
	    String email = SecurityContextHolder.getContext().getAuthentication().getName();
	    return userRepository.findByEmail(email).map(owner ->{
	        return contributionRepository.findById(panelId).map(panel ->{
	            if(!blogRepository.existsByUserAndContributionPanel(owner , panel)) 
	                throw new IllegalAccessRequestException("Faisled To Remove Contributor");

	            return userRepository.findById(userId).map(contributor ->{
	                if (panel.getUsers().remove(contributor)) {
	                    contributionRepository.save(panel);
	                    return ResponseEntity.ok(responseStrecture.setStatusCode(HttpStatus.OK.value()).setMessage("Contributor Removed Successfully").setData(panel));
	                } else {
	                    throw new UserIdIsNotFound("Entered User Id is Not Found in the Contribution Panel");
	                }
	            }).orElseThrow(()-> new UserNotFoundById("Entered User Id is Not Found "));
	        }).orElseThrow(()-> new PannelNotFoundByIdException("Entered panel Id is not Found"));
	    }).get();
	}
}
