package io.britto.serweja.core;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;

import io.britto.serweja.util.WebConfig;
import io.britto.serweja.util.WebLogger;
import io.ee.serweja.annotations.BrittoServlet;
import io.ee.serweja.http.BrittoHttpServlet;

public class ServletFinder {

	public static BrittoHttpServlet findServlet(String path) {

		try {
			String classFolder = WebConfig.DOCUMENT_FOLDER + WebConfig.APP_ROOT;
			File[] classes = new File(classFolder).listFiles();

			for (File className : classes) { // Para garantir segurança

				if (className.getName().endsWith(".class")) {

					File folderFile = new File(classFolder);
					URL url = folderFile.toURI().toURL();
					URL urls[] = new URL[] { url };
					URLClassLoader loader = new URLClassLoader(urls);

					Class<?> classRef = loader.loadClass(className.getName().substring(0, className.getName().indexOf(".class")));
					WebLogger.log("Found a Class: " + className.getName());

					for (Annotation an : classRef.getDeclaredAnnotations()) {
						System.out.println("CHEGUEI AQUI?");
						WebLogger.log("     + New Annotation: " + an.annotationType().getName());
						if(an.annotationType().getName().equals("io.ee.serweja.annotations.BrittoServlet")) {
							BrittoServlet servletAnnotation = (BrittoServlet) an;
							WebLogger.log("     + Request Path: " + servletAnnotation.path());
							if(servletAnnotation.path().equals(path)) {
								return (BrittoHttpServlet) classRef.getDeclaredConstructor().newInstance();	
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
