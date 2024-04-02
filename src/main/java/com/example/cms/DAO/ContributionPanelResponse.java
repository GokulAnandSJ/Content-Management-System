package com.example.cms.DAO;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.cms.entity.User;


@Component
public class ContributionPanelResponse {

	private int panelId;
	
	private List<UserResponse> users;
	
	

	public List<UserResponse> getUsers() {
		return users;
	}

	public ContributionPanelResponse setUsers(List<UserResponse> users) {
		this.users = users;
		return this;
	}

	public int getPanelId() {
		return panelId;
	}

	public ContributionPanelResponse setPanelId(int panelId) {
		this.panelId = panelId;
		return this;
		
	}

	
	
}
