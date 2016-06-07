package com.mygdx.colors.exceptions;

public class ResourceRepeatedInMapException extends Exception{
	
	private String resource;
	private static final long serialVersionUID = 1L;
	
	public ResourceRepeatedInMapException(String resource){
		this.resource = resource;
	}
	
	public String getMessage(){
		return "It looks like there's already a " + resource + " with the same key in the map";
	}
	
}
