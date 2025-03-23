package io.britto.serweja.util;

import java.util.HashMap;

public class WebConfig {

	public static final String DOCUMENT_ROOT="/Users/Samsung/Documents/SerWeJa";
	
	public static HashMap<String, String> content = new HashMap<>() {
	{
		put(".html", "text/html");
		put(".htm", "text/html");
		put(".jpg", "image/jpg");
		put(".png", "image/png");
		put(".jpeg", "image/jpeg");
		put(".txt", "text/plain");
		
	}};
}
