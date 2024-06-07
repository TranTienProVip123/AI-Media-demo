	package com.bezkoder.spring.thymeleaf.image.upload.model;
	
	
	public class ImageInfo {
	  
	  private String name;
	  private String url;
	  private String type;
	
	
	  public ImageInfo(String name, String url,String type) {
	    this.name = name;
	    this.url = url;
	    this.type= type;
	  }
	
	  public String getName() {
	    return this.name;
	  }
	
	  public void setName(String name) {
	    this.name = name;
	  }
	
	  public String getUrl() {
	    return this.url;
	  }
	
	  public void setUrl(String url) {
	    this.url = url;
	  }
	  public String getType() {
		  return this.type;
	  }
	  public void setType(String getType) {
		  this.type = type;
	  }
	}
