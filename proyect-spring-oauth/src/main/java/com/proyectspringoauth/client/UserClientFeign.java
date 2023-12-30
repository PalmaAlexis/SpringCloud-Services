package com.proyectspringoauth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.usercommons.model.entity.User;

@FeignClient("app-user")
public interface UserClientFeign {
	
	@GetMapping("users/search/findUser")
	public User findByUsername(@RequestParam("user")String username);
	
	@PutMapping("users/{id}")
	public User updateUserInfo(@RequestBody User u, @PathVariable Long id);
}
