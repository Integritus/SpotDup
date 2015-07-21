package com.santitosvk.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {
	
	@JsonProperty("track")
	private TrackInfo trackInfo;

	public TrackInfo getTrackInfo() {
		return trackInfo;
	}

	public void setTrackInfo(TrackInfo trackInfo) {
		this.trackInfo = trackInfo;
	}
	
	

}
