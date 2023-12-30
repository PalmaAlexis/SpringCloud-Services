package com.proyectspringoauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.proyectspringoauth.client.UserClientFeign;
import com.usercommons.model.entity.User;

@Component
public class TokenInfoEnhancer implements TokenEnhancer{
	
	@Autowired
	private UserClientFeign userClient;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		Map<String, Object> info= new HashMap<>();
		User user= userClient.findByUsername(authentication.getName());
		info.put("nombre", user.getName());
		info.put("apellido", user.getLastName());
		info.put("correo", user.getEmail());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}

}
