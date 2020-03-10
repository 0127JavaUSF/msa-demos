package com.revature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Zuul is a Gateway service. This kind of service is often also called a
 * "reverse proxy". A proxy is a server that makes requests on your behalf. 
 * In another sense, a proxy hides the requester's identity from the destination
 * server.
 * A reverse proxy instead hides the location of the destination service from
 * the requester.
 */

@EnableZuulProxy
@SpringBootApplication
public class Application {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
}
