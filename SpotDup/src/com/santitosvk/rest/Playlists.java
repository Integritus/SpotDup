package com.santitosvk.rest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Playlists extends Pagination {
	
	@JsonProperty("items")
	private List<PlaylistInfo> playlistInfo;
	
	public List<PlaylistInfo> getItems() {
		return playlistInfo;
	}

	public void setItems(List<PlaylistInfo> playlistInfo) {
		this.playlistInfo = playlistInfo;
	}
}
