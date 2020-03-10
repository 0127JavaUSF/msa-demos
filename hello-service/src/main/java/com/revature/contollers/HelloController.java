package com.revature.contollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@GetMapping("/**")
	public String handleGet() {
		return "Hello Cindy!";
	}
}
