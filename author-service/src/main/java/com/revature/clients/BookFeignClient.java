package com.revature.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

/**
 * Fiegn Client is an automatically implementing interface
 * that matches a foreign controller and implements the client
 * side of the controller logic such that requests to the foreign
 * controller can be called easily with the same syntax as already
 * exist in the controller.
 *
 */

@Profile("feignclient")
@FeignClient(url="http://localhost:7020/", 
	name="book-service", 
	fallback=BookFeignFallback.class)
public interface BookFeignClient extends BookClient {

}
