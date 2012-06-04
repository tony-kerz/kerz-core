package com.kerz.oauth;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.AuthorizedClientAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.OAuth2ProviderTokenServices;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class OauthServiceTest
{
	@Autowired
	private OAuth2ProviderTokenServices oa2pts;
	
	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void basic()
	{
		Set<String> scope = new HashSet<String>();
		scope.add("scopeElt1");
		scope.add("scopeElt2");
		Authentication clientAuth = new AuthorizedClientAuthenticationToken("client123", null, scope, null);
		Authentication userAuth = new UsernamePasswordAuthenticationToken("user.xyz@gmail.com", null);
		OAuth2Authentication oa2Auth = new OAuth2Authentication(clientAuth, userAuth);
		OAuth2AccessToken oa2Token = oa2pts.createAccessToken(oa2Auth);
		System.out.println("oa2Token=" + oa2Token);
		oa2Auth = oa2pts.loadAuthentication(oa2Token.getValue());
		System.out.println("oa2Auth=" + oa2Auth);
	}
}
