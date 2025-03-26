package io.britto.serweja.util;

import java.util.HashMap;

public class WebConfig {
	
	public static final String DOCUMENT_FOLDER="/Users/Samsung/Documents/SerWeJa";
	public static final String DOC_ROOT="/SMP-FLD"; //Simple Folder
	public static final String APP_ROOT="/WEB-FLD"; //Web Folder
	
	public static HashMap<String, String> content = new HashMap<>() {
	{
		put(".html", "text/html");
		put(".htm", "text/html");
		put(".jpg", "image/jpg");
		put(".png", "image/png");
		put(".jpeg", "image/jpeg");
		put(".txt", "text/plain");
	}};
	
	public static HashMap<Integer, String> textCodes = new HashMap<>() {{
		put(200, "OK");
		put(400, "BAD REQUEST");
		put(401, "UNAUTHORIZED");
		put(403, "FORBIDDEN");
		put(404, "NOT FOUND");
		put(405, "METHOD NOT ALLOWED");
		put(500, "INTERNAL SERVER ERROR");
	}};
}
