package com.devsuperior.bds04.components;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.devsuperior.bds04.entities.User;
import com.devsuperior.bds04.repositories.UserRepository;

public class JwtTokenEnhancer implements TokenEnhancer {

	private UserRepository userRepository;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		User user = userRepository.findByEmail(authentication.getName());

		Map<String, Object> map = new HashMap<>();
		map.put("userFirstName", user.getFirstName());
		map.put("userId", user.getId());

		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
		token.setAdditionalInformation(map);

		return accessToken;
		
	}

}
