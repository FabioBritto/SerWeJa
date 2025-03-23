package io.britto.serweja.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

	private ServerSocket serverSocket;
	
	private String httpMethod;
	private String resourcePath;
	
	public WebServer(int port) {
		try {
			this.serverSocket = new ServerSocket(port);
			System.out.println("SerWeJa started on Port: " + port);
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
				if(line.startsWith("GET") || line.startsWith("POST")) {
					httpMethod = line.substring(0,line.indexOf(" "));
					resourcePath = line.substring(line.indexOf(" "), line.lastIndexOf(" "));
					
					handleOutput(httpMethod, resourcePath);
				}
				//System.out.println("DEBUG - " + line);
			}while(!line.isBlank());
			socket.close();
		}
		catch(IOException e) {
			System.err.println("Error on Handle Request");
		}
	}
	
	private void handleOutput(String httpMethod, String resourcePath) {
		System.out.println("HTTP Methdo: " + httpMethod);
		System.out.println("Resource Path: " + resourcePath);
	}
}
