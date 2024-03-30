package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.entity.ContributionPanel;
import com.example.cms.service.ContributionPanelService;
import com.example.cms.utility.ResponseStructure;

@RestController
public class ContributionPanelControlletr {

	private ContributionPanelService contributionService;

	public ContributionPanelControlletr(ContributionPanelService contributionService) {
		super();
		this.contributionService = contributionService;
	}
	
	@PutMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanel>> addUserToContributionPanel(@PathVariable int userId , @PathVariable int panelId){
		return contributionService.addUserToContributionPanel(userId, panelId);
	}
	
	@DeleteMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanel>> removeUserFromContributionPanel(@PathVariable int userId , @PathVariable int panelId){
		return contributionService.removeUserFromContributionPanel(userId, panelId);
	}

	
}
