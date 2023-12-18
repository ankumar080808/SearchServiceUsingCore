package com.vinculum.ondc;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.glassfish.jersey.server.ResourceConfig;

import com.vinculum.ondc.constants.Constants;

/**
 * The Main Service Class. This is the start point
 * of the VIN-PAC Service. It Loads the Spring context
 * and starts up the application.
 *
 */

public class OndcSearchApplication extends ResourceConfig {
	
	 public OndcSearchApplication() {
	        register(VinRequestFilter.class);
	    }
	/**	
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(final String[] args) {
		
		start();
	}
	
	

	public static void start(){
		//Put Unique Key For Main Logging
		MDC.put(Constants.UNIQUE, Constants.MAIN);
		//Initialize the context
		@SuppressWarnings("resource")
		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/ondc-spring-context.xml");
		//Register The Shutdown Hook
		context.registerShutdownHook();

		//Remove The Key
		MDC.remove(Constants.UNIQUE);
	}

	public static void stop(final String[]args){
		//Exit from the system
		System.exit(0);
	}
}
