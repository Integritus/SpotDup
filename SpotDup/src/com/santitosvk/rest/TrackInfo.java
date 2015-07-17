package com.santitosvk.rest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackInfo {
	
	@JsonProperty("album")
	private Album album;
	
	@JsonProperty("artists")
	private List<Artist> artists;
	
	@JsonProperty("duration_ms")
	private int duration;
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("name")
	private int name;
	
	@JsonProperty("uri")
	private int uri;

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getUri() {
		return uri;
	}

	public void setUri(int uri) {
		this.uri = uri;
	}
	
	

}
