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
			System.out.println("Waiting for Connections...");
		}
		catch(IOException ex) {
			System.out.println("Could not initialize SerWeJa on Port: " + port);
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
				System.out.println("Couldn't handle client request");
			}
		}
	}
	
	public WebServer() {
		this(80);
	}
	
	
	private void handleRequest(Socket socket) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = null;
			do {
				line = br.readLine();
				if(line != null && (line.startsWith("GET") || line.startsWith("POST"))) {
					httpMethod = line.substring(0,line.indexOf(" "));
					resourcePath = line.substring(line.indexOf(" ") + 1, line.lastIndexOf(" "));
					
					handleOutput(socket, httpMethod, resourcePath);
				}
				//System.out.println("DEBUG - " + line);
			}while(!line.isBlank());
			socket.close();
		}
		catch(IOException e) {
			System.err.println("Error on Handle Request");
		}
	}
	
	private void handleOutput(Socket socket, String httpMethod, String resourcePath) {
		System.out.println("HTTP Methdo: " + httpMethod);
		System.out.println("Resource Path: " + resourcePath);
		
		//resourcePath = resourcePath.replace("/", "\\");		
		String completePath = WebConfig.DOCUMENT_ROOT + resourcePath;
		//completePath = completePath.replace(" ", "");
		
		try {
			OutputStream out = socket.getOutputStream();
			if(Files.exists(Paths.get(completePath))){
				System.out.println("FILE EXISTS");
				System.out.println("Complete Path: " + completePath);
				byte[] content = Files.readAllBytes(Paths.get(completePath));
				String extension = completePath.substring(completePath.lastIndexOf("."));
				out.write("HTTP/1.1 200 OK\r\n" .getBytes());
				out.write(("Date:" + LocalDate.now().toString() + "\r\n").getBytes());
				out.write(("Content-Type:" + WebConfig.content.get(extension) + "\r\n").getBytes());
				out.write(("Content-Lenght:" + content.length + "\r\n").getBytes());
				out.write("\r\n" .getBytes());
				out.write(content);
				out.write("\r\n\r\n" .getBytes());
				out.flush();
				out.close();
			}
			else {
				System.out.println("404 - NOT FOUND");
			}
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
}
