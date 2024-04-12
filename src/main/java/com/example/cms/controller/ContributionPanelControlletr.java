package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.DAO.ContributionPanelResponse;
import com.example.cms.service.ContributionPanelService;
import com.example.cms.utility.ErrorStructure;
import com.example.cms.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class ContributionPanelControlletr {

	private ContributionPanelService contributionService;

	public ContributionPanelControlletr(ContributionPanelService contributionService) {
		super();
		this.contributionService = contributionService;
	}
	
	@Operation(description = "this endpoint is used to Add Contributior to the Panel" , responses = {
			@ApiResponse(responseCode = "200", description = "Contributior Added Sucessfully"),
			@ApiResponse(responseCode = "400" , description = "Invalid Input" , content = @Content(schema = @Schema(implementation = ErrorStructure.class ))),
			@ApiResponse(responseCode = "404" , description = "Invalid Input" , content = @Content(schema = @Schema(implementation = ErrorStructure.class )))})
	@PutMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanelResponse>> addUserToContributionPanel(@PathVariable int userId , @PathVariable int panelId){
		return contributionService.addUserToContributionPanel(userId, panelId);
	}
	
	
	@Operation(description = "this endpoint is used to remove Contributior from the Panel" , responses = {
			@ApiResponse(responseCode = "200", description = "Contributior remove Sucessfully"),
			@ApiResponse(responseCode = "400" , description = "Invalid Input" , content = @Content(schema = @Schema(implementation = ErrorStructure.class ))),
			@ApiResponse(responseCode = "404" , description = "Invalid Input" , content = @Content(schema = @Schema(implementation = ErrorStructure.class )))})
	@DeleteMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<ContributionPanelResponse>> removeUserFromContributionPanel(@PathVariable int userId , @PathVariable int panelId){
		return contributionService.removeUserFromContributionPanel(userId, panelId);
	}

	
}
