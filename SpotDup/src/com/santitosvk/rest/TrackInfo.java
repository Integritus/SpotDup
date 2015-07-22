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
	private String id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("uri")
	private String uri;
	
	@JsonProperty("linked_from")
	private TrackLink linkedFrom;
	

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public TrackLink getLinkedFrom() {
		return linkedFrom;
	}

	public void setLinkedFrom(TrackLink linkedFrom) {
		this.linkedFrom = linkedFrom;
	}
	
	

}
