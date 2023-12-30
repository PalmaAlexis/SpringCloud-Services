package com.proyectspringoauth.services;

import com.usercommons.model.entity.User;

public interface IUserService {
	
	public User findByUsername(String username);
	
	public User updateUserInfo(User u, Long id);
	

}
