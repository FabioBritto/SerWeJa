package io.britto.serweja.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import io.britto.serweja.http.Request;
import io.britto.serweja.util.WebConfig;
import io.britto.serweja.util.WebLogger;

public class WebServer {

	private ServerSocket serverSocket;
	
	private String httpMethod;
	private String resourcePath;
	
	public WebServer(int port) {
		try {
			WebLogger.welcome(port);
			this.serverSocket = new ServerSocket(port);
			WebLogger.log("Started on Port: " + port);
			WebLogger.log("Waiting for Connections...");
		}
		catch(IOException ex) {
			WebLogger.log("Could not initialize SerWeJa on Port: " + port);
			return;
		}
		/*
		 * Loop Infinito para poder esperar, enquanto estiver ON, a requisição do Cliente
		 */
		while(true) {
			try {
				/*
				 * No accept() ele cria um novo socket e começa a troca de dados.
				 * É neste momento que ele cria um socket para lidar com o Cliente
				 */
				Socket socket = serverSocket.accept(); //Aceito trocar dados com o cliente
				handleRequest(socket);
				
			}
			catch(IOException ex) {
				WebLogger.log("Couldn't handle client request");
			}
		}
	}
	
	public WebServer() {
		this(80);
	}
	
	
	private void handleRequest(Socket socket) {
		try {
			Request request = new Request();
			
			InputStreamReader inReader = new InputStreamReader(socket.getInputStream());
			
			BufferedReader br = new BufferedReader(inReader);
			String line = null;
			do {
				line = br.readLine();
				if(line != null && (line.startsWith("GET") || line.startsWith("POST"))) {

					httpMethod = line.substring(0,line.indexOf(" "));
					resourcePath = line.substring(line.indexOf(" ") + 1, line.lastIndexOf(" "));
					
					request.setHttpMethod(httpMethod);
					int paramDelimiter = resourcePath.indexOf('?') == -1 ? resourcePath.length() : resourcePath.indexOf('?');
					String resourceFile = resourcePath.substring(0, paramDelimiter);
					request.setPath(resourceFile);
					
					if(resourcePath.indexOf('?') > 0) {
						String parameterListStr = resourcePath.substring(paramDelimiter + 1);
						String[] paramList = parameterListStr.split("&");
						for(String p : paramList) {
							String[] keyValue = p.split("=");						
							request.addParameter(keyValue[0], keyValue[1]);
						}
					}
					
					WebLogger.log(request.toString());
					
					 
				}
				else if(line != null && !line.isBlank()){
					//String headers[] = line.split(":", 1);
					String headers[] = line.split(":");
					request.addHeader(headers[0], headers[1]);
				}
				
			}while(line != null && !line.isBlank());
			
			if(request.getHttpMethod() != null && request.getHttpMethod().equals("POST")) {
				line = null;
				WebLogger.log("Reading " + request.getHeader("Content-Length") + " bytes of" + request.getHeader("Content-Type"));
				
				
				int len = Integer.parseInt(request.getHeader("Content-Length").trim());
				char buf[] = new char[len];
				br.read(buf);
				request.setBody(new String(buf));
			}
			handleOutput(socket, request);
			socket.close();
		}
		catch(IOException e) {
			WebLogger.log("Error on Handle Request");
		}
	}
	
	private void handleOutput(Socket socket, Request request) {
//		WebLogger.log("HTTP Method: " + httpMethod);
//		WebLogger.log("Resource Path: " + resourcePath);
		
		//resourcePath = resourcePath.replace("/", "\\");		
		String completePath = WebConfig.DOCUMENT_ROOT + request.getPath();
		//completePath = completePath.replace(" ", "");
		
		try {
			OutputStream out = socket.getOutputStream();
			if(Files.exists(Paths.get(completePath))){
				WebLogger.log("Complete Path: " + completePath);
				byte[] content = Files.readAllBytes(Paths.get(completePath));
				String extension = completePath.substring(completePath.lastIndexOf("."));
				out.write("HTTP/1.1 200 OK\r\n" .getBytes());
				out.write(("Date:" + LocalDate.now().toString() + "\r\n").getBytes());
				out.write(("Content-Type:" + WebConfig.content.get(extension) + "\r\n").getBytes());
				out.write(("Content-Length:" + content.length + "\r\n").getBytes());
				out.write("\r\n" .getBytes());
				out.write(content);
				out.write("\r\n\r\n" .getBytes());
				out.flush();
				out.close();
			}
			else {
				WebLogger.log("404 - NOT FOUND");
			}
		}
		catch(IOException e){
			WebLogger.log(e.getMessage());
		}
	}
}
