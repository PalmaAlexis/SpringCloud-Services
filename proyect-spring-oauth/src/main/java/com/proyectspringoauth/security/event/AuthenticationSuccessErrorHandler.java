package com.proyectspringoauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import com.proyectspringoauth.services.IUserService;
import com.usercommons.model.entity.User;

import brave.Tracer;
import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	private final Logger logg = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

	@Autowired
	private IUserService userService;
	//private UserClientFeign userClient;
	
	@Autowired
	private Tracer trace;

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		if (authentication.getDetails() instanceof WebAuthenticationDetails) {
			return;
		}
		logg.info("LOGIN SUCCESS CON USER: " + userDetails.getUsername());

		User user = userService.findByUsername(userDetails.getUsername());
		user.setAttempts(0);
		userService.updateUserInfo(user, user.getId());

	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		StringBuilder errors= new StringBuilder();
		String error= "LOGIN FAILED: " + exception.getMessage();
		logg.error(error);
		errors.append(error);
		try {
			User user = userService.findByUsername(authentication.getName());
			if(user.getAttempts() >=3) {
				String maxTries= "USER WITH USERNAME: " +  authentication.getName()+ " HAS BEEN DISABLED BECAUSE OF MAXIMIUM ATTEMTPS";
				user.setEnable(false);
				logg.error(maxTries);
				errors.append(" - "+maxTries);
			}
			
			if (null == user.getAttempts()) {
				user.setAttempts(0);
			}
			user.setAttempts(user.getAttempts() + 1);
			if(user.getAttempts() <=3) {
				String tryError= "USER WITH USERNAME: " +  authentication.getName()+ " HAS "+(3-user.getAttempts())+" ATTEMPS LEFT"; 
				logg.error(tryError);
				errors.append(" - "+tryError);
			}	
			
			trace.currentSpan().tag("mensaje-error", errors.toString());
			userService.updateUserInfo(user, user.getId());
		} catch (FeignException e) {
			logg.error("NO USER WITH USERNAME: " + authentication.getName());
		}

	}

}
