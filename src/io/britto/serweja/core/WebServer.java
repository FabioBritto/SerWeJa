package io.britto.serweja.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

	private ServerSocket serverSocket;
	
	public WebServer(int port) {
		try {
			this.serverSocket = new ServerSocket(port);
			System.out.println("SerWeJa started on Port: " + port);
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
				socket.close();
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
		
	}
}
