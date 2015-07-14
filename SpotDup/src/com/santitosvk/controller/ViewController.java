package com.santitosvk.controller;

import java.nio.charset.Charset;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.util.URLEncoder;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.santitosvk.rest.Tokens;
import com.santitosvk.rest.User;

@Controller
public class ViewController {
	
	static final String clientId = "6fe801c759e1431ea70de6d95469b0ec"; // Your client id
	static final String clientSecret = "e92cab63d8d24438bfed633ac17612d4"; // Your client secret
	static final String redirectUri = "http://localhost:8080/SpotDup/callback"; // Your redirect uri
	
	static final String stateKey = "spotify_auth_state";//cookie;

	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@CookieValue(value = stateKey, defaultValue = "") String cookie,
						HttpServletResponse response) {
		//RestTemplate rest = new RestTemplate();
		if (cookie.isEmpty()) {
			cookie = generateRandomString(16);
			response.addCookie(new Cookie(stateKey, cookie));
		}
		String scope = "user-read-private user-read-email";
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl("https://accounts.spotify.com/authorize");
		urlBuilder.queryParam("client_id", clientId);
		urlBuilder.queryParam("response_type", "code");
		urlBuilder.queryParam("redirect_uri", redirectUri);
		urlBuilder.queryParam("state", cookie);
		urlBuilder.queryParam("scope", scope);
		
		String url1 = urlBuilder.toUriString();
		//String url2 = urlBuilder.build(true).toUriString();
		//String url3 = urlBuilder.build(false).toUriString();
		
		//String r = rest.getForObject(url1, String.class);
		 
		 
 		return "redirect:" + url1;
	}
	
	@RequestMapping(value = "/callback", method = RequestMethod.GET)
	public <T> String callback(@RequestParam(value = "code", defaultValue = "") String code, 
						   @RequestParam("state") String state,
						   @RequestParam(value = "error", defaultValue = "") String error,
						   @CookieValue(value = stateKey) Cookie cookie,
						   HttpServletResponse response) {
		String c = code;
		String s = state;
		String e = error;
		if(state == null || !state.equals(cookie.getValue())){
			return "redirect:/#?error=state_mismatch";
		} else {
			response.addCookie(new Cookie(stateKey, ""));//reset cookie
			
			//Initial Auth
			HttpHeaders headers = new HttpHeaders();
			String auth = clientId + ":" + clientSecret;
	        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")) );
	        String authHeader = "Basic " + new String( encodedAuth );
			headers.set(HttpHeaders.AUTHORIZATION, authHeader);
			
			//Construct URI for token
			UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl("https://accounts.spotify.com/api/token");
			urlBuilder.queryParam("code", code);
			urlBuilder.queryParam("redirect_uri", redirectUri);
			urlBuilder.queryParam("grant_type", "authorization_code");			
			String url1 = urlBuilder.toUriString();
			
			RestTemplate restTemplate = new RestTemplate();//@TODO add to spring config for autoinject
			//Get tokens in REST
			ResponseEntity<Tokens> responseToken = restTemplate.exchange(url1, HttpMethod.POST, new HttpEntity<T>(headers), Tokens.class);
			Tokens token = responseToken.getBody();
			
			HttpHeaders headersBearer = createHeader(token);//create header with token
			
			String urlUser = "https://api.spotify.com/v1/me";//api uri for user info, only need id
			ResponseEntity<User> responsesUser = restTemplate.exchange(urlUser, HttpMethod.GET, new HttpEntity<T>(headersBearer), User.class);
			User user = responsesUser.getBody();
			user.getId();
			
			
		}
 		return "helloworld";
	}
		 		 
	private HttpHeaders createHeader(Tokens token) {
		HttpHeaders headers = new HttpHeaders();
		String authHeader = "Bearer " + new String( token.getAccessToken() );
		headers.set(HttpHeaders.AUTHORIZATION, authHeader);		
		return headers;
	}
	
	
	
	private String generateRandomString(int length) {
		  String text = "";
		  String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		  for (int i = 0; i < length; i++) {
		    text += possible.charAt((int)Math.floor(Math.random() * possible.length()));
		  }
		  return text;
		}
	
}
