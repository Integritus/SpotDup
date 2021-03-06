package com.santitosvk.rest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tracks extends Pagination {
	
	@JsonProperty("items")
	private List<Track> trackInfo;
	
	public List<Track> getItems() {
		return trackInfo;
	}

	public void setItems(List<Track> trackInfo) {
		this.trackInfo = trackInfo;
	}

}
