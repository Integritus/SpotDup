package com.santitosvk.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRedirect {
	private String responseType;
	private String clientId;
	private String scope;
	private String redirectUri;
	private String state;
	
}
