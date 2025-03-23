package io.britto.serweja.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WebLogger {

	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String WHITE = "\u001B[37m";
	public static final String RESET = "\u001B[0m";

	public static DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	public static void welcome(int port) {
		System.out.println(YELLOW);
		System.out.println("   _____          _       __         __     ");
		System.out.println("  / ___/___  ____| |     / /__      / /___ _      By FabioBritto");
		System.out.println("  \\__ \\/ _ \\/ ___/ | /| / / _ \\__  / / __ `/      For Study Purposes");
		System.out.println(
				" ___/ /  __/ /   | |/ |/ /  __/ /_/ / /_/ /       Content learned with Professor Isidro on IsiFlix -> https://www.isiflix.com.br");
		System.out.println("/____/\\___/_/    |__/|__/\\___/\\____/\\__,_/        WebServer made with Java");
		System.out.println(RESET);
	}

	public static void log(String message) {
		
		String date = LocalDateTime.now().format(formatDate);

		System.out.printf(GREEN + "%15s\t" + YELLOW + "%-30s" + "\n" + RESET, date, message);
	}
}
