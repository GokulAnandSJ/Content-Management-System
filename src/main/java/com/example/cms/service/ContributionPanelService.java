package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.DAO.ContributionPanelResponse;
import com.example.cms.entity.ContributionPanel;
import com.example.cms.utility.ResponseStructure;

public interface ContributionPanelService {

	ResponseEntity<ResponseStructure<ContributionPanelResponse>> addUserToContributionPanel(int userId , int panelId);
	
	ResponseEntity<ResponseStructure<ContributionPanelResponse>> removeUserFromContributionPanel(int userId , int panelId);
}
