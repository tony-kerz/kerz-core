package com.kerz.oauth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.AuthorizedClientAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.RandomValueOAuth2ProviderTokenServices;
import org.springframework.stereotype.Service;

import com.kerz.oauth.dao.jpa.TokenedAuthenticationDao;
import com.kerz.oauth.domain.TokenedAuthentication;
import com.kerz.util.StringHelper;

@Service
@SuppressWarnings("rawtypes")
public class DefaultOAuth2ProviderTokenServices extends RandomValueOAuth2ProviderTokenServices
{
	private Logger log = LoggerFactory.getLogger(DefaultOAuth2ProviderTokenServices.class);
	
	private TokenedAuthenticationDao tokenedAuthenticationDao;
	
	@Override
	protected OAuth2AccessToken readAccessToken(String tokenValue)
	{
		log.debug("token={}", tokenValue);
		TokenedAuthentication ta = tokenedAuthenticationDao.findOneByTokenValue(tokenValue);
		OAuth2AccessToken oa2at = new OAuth2AccessToken();
		oa2at.setValue(ta.getTokenValue());
		oa2at.setTokenType(ta.getTokenType());
		oa2at.setExpiration(ta.getExpiration());
		OAuth2RefreshToken oa2rt = new OAuth2RefreshToken();
		oa2rt.setValue(ta.getRefreshTokenValue());
		oa2at.setRefreshToken(oa2rt);
		oa2at.setScope(StringHelper.setFromCsv(ta.getScopeCsv()));
		return oa2at;
	}
	
	@Override
	protected OAuth2Authentication readAuthentication(ExpiringOAuth2RefreshToken token)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected OAuth2Authentication readAuthentication(OAuth2AccessToken token)
	{
		log.debug("token={}", token);
		TokenedAuthentication ta = tokenedAuthenticationDao.findOneByTokenValue(token.getValue());
		AuthorizedClientAuthenticationToken clientAuthentication = new AuthorizedClientAuthenticationToken(
		    ta.getClientId(), null, StringHelper.setFromCsv(ta.getScopeCsv()), null);
		UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(ta.getUserId(),
		    null);
		OAuth2Authentication oa2a = new OAuth2Authentication<Authentication, Authentication>(clientAuthentication,
		    userAuthentication);
		return oa2a;
	}
	
	@Override
	protected ExpiringOAuth2RefreshToken readRefreshToken(String tokenValue)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected void removeAccessToken(String tokenValue)
	{
		log.debug("token={}", tokenValue);
		TokenedAuthentication ta = tokenedAuthenticationDao.findOneByTokenValue(tokenValue);
		tokenedAuthenticationDao.delete(ta);
	}
	
	@Override
	protected void removeAccessTokenUsingRefreshToken(String refreshToken)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected void removeRefreshToken(String tokenValue)
	{
		throw new UnsupportedOperationException();
	}
	
	@Autowired
	public void setTokenedAuthenticationDao(TokenedAuthenticationDao tokenedAuthenticationDao)
	{
		this.tokenedAuthenticationDao = tokenedAuthenticationDao;
	}
	
	@Override
	protected void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication)
	{
		TokenedAuthentication ta = new TokenedAuthentication();
		Authentication clientAuth = authentication.getClientAuthentication();
		Authentication userAuth = authentication.getUserAuthentication();
		ta.setTokenValue(token.getValue());
		ta.setTokenType(token.getTokenType());
		ta.setExpiration(token.getExpiration());
		ta.setScopeCsv(StringHelper.csvFromSet(token.getScope()));
		if (token.getRefreshToken() != null)
		{
			ta.setRefreshTokenValue(token.getRefreshToken().getValue());
		}
		ta.setClientId(clientAuth.getName());
		ta.setUserId(userAuth.getName());
		tokenedAuthenticationDao.save(ta);
		log.debug("tokenedAuthentication={}", ta);
	}
	
	@Override
	protected void storeRefreshToken(ExpiringOAuth2RefreshToken refreshToken, OAuth2Authentication authentication)
	{
		throw new UnsupportedOperationException();
	}
}
