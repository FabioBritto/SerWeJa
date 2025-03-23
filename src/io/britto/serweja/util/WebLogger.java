package io.britto.serweja.util;

public class WebLogger {

	
	/*
   _____          _       __         __     
  / ___/___  ____| |     / /__      / /___ _
  \__ \/ _ \/ ___/ | /| / / _ \__  / / __ `/
 ___/ /  __/ /   | |/ |/ /  __/ /_/ / /_/ / 
/____/\___/_/    |__/|__/\___/\____/\__,_/  
                                            
	 
	 
	 
	 
	 
	 
	 */
	
	public static final String GREEN  = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String WHITE  = "\u001B[37m";
	public static final String RESET  = "\u001B[0m";
	
	
	public static void welcome(int port) {
		System.out.println(YELLOW);
		System.out.println("   _____          _       __         __     ");
		System.out.println("  / ___/___  ____| |     / /__      / /___ _      By FabioBritto");
		System.out.println("  \\__ \\/ _ \\/ ___/ | /| / / _ \\__  / / __ `/      For Study Purposes");
		System.out.println(" ___/ /  __/ /   | |/ |/ /  __/ /_/ / /_/ /       Content learned with Professor Isidro on IsiFlix -> https://www.isiflix.com.br");
		System.out.println("/____/\\___/_/    |__/|__/\\___/\\____/\\__,_/        Starded on Port: " + port + " ||  WebServer made with Java");
		System.out.println(RESET);
	}
}
