package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.entity.ContributionPanel;
import com.example.cms.utility.ResponseStructure;

public interface ContributionPanelService {

	ResponseEntity<ResponseStructure<ContributionPanel>> addUserToContributionPanel(int userId , int panelId);
	
	ResponseEntity<ResponseStructure<ContributionPanel>> removeUserFromContributionPanel(int userId , int panelId);
}
