 package io.britto.serweja.http;

import java.util.HashMap;

public class Request {

	private String httpMethod;
	private String path;
	private String body;
	
	private HashMap<String, String> requestParameters;
	private HashMap<String, String> headers;
	
	public Request() {
		requestParameters = new HashMap<>();
		headers = new HashMap<>();
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void addParameter(String key, String value) {
		requestParameters.put(key.toLowerCase(), value);
	}
	
	public String getParameter(String key) {
		return requestParameters.get(key.toLowerCase());
	}
	
	public String getHeader(String key) {
		return headers.get(key.toLowerCase());
	}
	
	public void addHeader(String key, String value) {
		headers.put(key.toLowerCase(), value);
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Request [httpMethod=" + httpMethod + ", path=" + path + ", body=" + body + "]";
	}
}
