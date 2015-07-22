package com.santitosvk.controller;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.util.URLEncoder;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.santitosvk.rest.PlaylistInfo;
import com.santitosvk.rest.Playlists;
import com.santitosvk.rest.Tokens;
import com.santitosvk.rest.TrackInfo;
import com.santitosvk.rest.Tracks;
import com.santitosvk.rest.Track;
import com.santitosvk.rest.User;

@Controller
public class ViewController {
	
	static final String clientId = "6fe801c759e1431ea70de6d95469b0ec"; // Your client id
	static final String clientSecret = "e92cab63d8d24438bfed633ac17612d4"; // Your client secret
	static final String redirectUri = "http://localhost:8080/SpotDup/callback"; // Your redirect uri
	
	static final String stateKey = "spotify_auth_state";//cookie;
	
	@Autowired
	private Tokens token;
	
	@Autowired
	private User user;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HttpHeaders headersBearer;
	

	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@CookieValue(value = stateKey, defaultValue = "") String cookie,
						HttpServletResponse response) {
		if (cookie.isEmpty()) {
			cookie = generateRandomString(16);
			response.addCookie(new Cookie(stateKey, cookie));
		}
		String scope = "playlist-read-private playlist-modify-public playlist-modify-private";
		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl("https://accounts.spotify.com/authorize");
		urlBuilder.queryParam("client_id", clientId);
		urlBuilder.queryParam("response_type", "code");
		urlBuilder.queryParam("redirect_uri", redirectUri);
		urlBuilder.queryParam("state", cookie);
		urlBuilder.queryParam("scope", scope);
		
		String url1 = urlBuilder.toUriString();		 
		 
 		return "redirect:" + url1;
	}
	
	@RequestMapping(value = "/callback", method = RequestMethod.GET)
	public <T> String callback(@RequestParam(value = "code", defaultValue = "") String code, 
						   @RequestParam("state") String state,
						   @RequestParam(value = "error", defaultValue = "") String error,
						   @CookieValue(value = stateKey) Cookie cookie,
						   HttpServletResponse response) {
		
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
			
			//RestTemplate restTemplate = new RestTemplate();//@TODO add to spring config for autoinject
			//Get tokens in REST
			ResponseEntity<Tokens> responseToken = restTemplate.exchange(url1, HttpMethod.POST, new HttpEntity<T>(headers), Tokens.class);
			token = responseToken.getBody();
			
			headersBearer = createHeader(token);//create header with token
			
			String urlUser = "https://api.spotify.com/v1/me";//api uri for user info, only need id
			ResponseEntity<User> responsesUser = restTemplate.exchange(urlUser, HttpMethod.GET, new HttpEntity<T>(headersBearer), User.class);
			user = responsesUser.getBody();			
		}
 		return "redirect:/showPlaylists";
	}
	
	@RequestMapping(value = "/showPlaylists", method = RequestMethod.GET)
	public <T> String showPlaylists(Model m) {
		
		String urlPlaylists = "https://api.spotify.com/v1/users/" + user.getId() + "/playlists";//api uri for user playlist
		ResponseEntity<Playlists> responsesPlaylist = restTemplate.exchange(urlPlaylists, HttpMethod.GET, new HttpEntity<T>(headersBearer), Playlists.class);
		Playlists playlists = responsesPlaylist.getBody();
		
		List<PlaylistInfo> playlist = playlists.getItems();
		List<PlaylistInfo> cleanPlaylist = new ArrayList<PlaylistInfo>();
		
		
		for(PlaylistInfo p : playlist){
			if(user.getId().equals(p.getOwner().getId())){
				cleanPlaylist.add(p);
			}
		}
		
		
		
		m.addAttribute("playlists", cleanPlaylist);
		
 		return "showplaylists";
	}
	
	@RequestMapping(value = "/findDuplicates/{playlistId}", method = RequestMethod.GET)
	public <T> String findDuplicates(@PathVariable String playlistId, Model m) {
		
		String urlPlaylists = "https://api.spotify.com/v1/users/" + user.getId() + "/playlists/" + playlistId + "/tracks";//api uri for playlist tracks
		ResponseEntity<Tracks> responsesPlaylist = restTemplate.exchange(urlPlaylists, HttpMethod.GET, new HttpEntity<T>(headersBearer), Tracks.class);
		Tracks tracks = responsesPlaylist.getBody();
		
		List<Track> trackInfo = tracks.getItems();
		
		while(tracks.getNext() != null){
			responsesPlaylist = restTemplate.exchange(tracks.getNext(), HttpMethod.GET, new HttpEntity<T>(headersBearer), Tracks.class);
			tracks = responsesPlaylist.getBody();
			trackInfo.addAll(tracks.getItems());
		}
		
		Map<String, Integer> trackCount = new HashMap<String, Integer>();
		List<TrackInfo> duplicates = new ArrayList<TrackInfo>();
		
		for(Track ti : trackInfo){
			if (!trackCount.containsKey(ti.getTrackInfo().getId())){
				trackCount.put(ti.getTrackInfo().getId(), 1);
			} else {
				int count = trackCount.get(ti.getTrackInfo().getId());
				System.out.println(count);
				if(!(count>1)){
					trackCount.put(ti.getTrackInfo().getId(), count+1);
					duplicates.add(ti.getTrackInfo());
				} 			
			}
		}
		
		for(TrackInfo ti : duplicates){
			ResponseEntity<String> res;
			String uri = ti.getUri();
			HttpEntity<String> entity;
			String URL = "https://api.spotify.com/v1/users/" + user.getId() + "/playlists/" + playlistId + "/tracks";
			
			if(ti.getLinkedFrom() != null) {
				//deleting tracks with linked from uri
				entity = new HttpEntity<String>("{\"tracks\": [ { \"uri\": \"" +  ti.getLinkedFrom().getUri() + "\" } ] }", headersBearer);
				res = restTemplate.exchange(URL, HttpMethod.DELETE, entity, String.class);
			} 
			//delting all track with uri
			entity = new HttpEntity<String>("{\"tracks\": [ { \"uri\": \"" +  uri + "\" } ] }", headersBearer);
			res = restTemplate.exchange(URL, HttpMethod.DELETE, entity, String.class);
			
			//Inserting a fresh copy of the track so we dont lose it
			UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(URL);
			urlBuilder.queryParam("uris", uri);
			urlPlaylists = urlBuilder.toUriString();
			res = restTemplate.exchange(urlPlaylists, HttpMethod.POST, new HttpEntity<T>(headersBearer), String.class);
		}
		
		m.addAttribute("tracks", duplicates);
		
 		return "showduplicates";
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
