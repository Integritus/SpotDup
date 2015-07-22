package com.santitosvk.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackLink {
	
	@JsonProperty("href")
	private String href;//A link to the Web API endpoint providing full details of the track.
	
	@JsonProperty("id")
	private String id;//The Spotify ID for the track.
	
	@JsonProperty("type")
	private String type;//The object type: "track".
	
	@JsonProperty("uri")
	private String uri;//The Spotify URI for the track.

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	

}
