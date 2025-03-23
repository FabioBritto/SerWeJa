package io.britto.serweja.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class Response {

	private OutputStream out;
	private String date;
	private int statusCode;
	private byte[] content;
	private HashMap<String, String> headers;
	
	private static final String ENDL ="\r\n";

	
	public Response(OutputStream out) {
		this.headers = new HashMap<String, String>();
		this.out = out;
	}
	
	public void write(String message) {
		try {
			out.write((message).getBytes());
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void write(byte[] message) {
		try {
			out.write(message);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	 }
	
	public void setHeader(String key, String value) {
		this.headers.put(key, value);
	}
	
	public void setContent(byte[] content) {
		this.content = content;
		this.write("Content-Length:" + content.length + ENDL);
		this.write(ENDL);
		this.write(content);
		this.write(ENDL + ENDL);
	}
	
	public void close() {
		try {
			out.flush();
			out.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
