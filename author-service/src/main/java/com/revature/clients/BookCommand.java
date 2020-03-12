package com.revature.clients;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.revature.dto.BookDTO;

@Component
@Profile("resttemplatehystrix")
public class BookCommand implements BookClient{

	public List<BookDTO> getBooksFallback(int id) {
		return null;
	}
	
	// A fallback method is a method with the same signature that will be
	// called in the case of a failure
	@Override
	@HystrixCommand(fallbackMethod = "getBooksFallback")
	public List<BookDTO> findBooksByAuthorId(int id) {
		RestTemplate template = new RestTemplate();
		String url = "http://localhost:8080/books/author/" + id;
		BookDTO[] bookArr = template.getForObject(url, BookDTO[].class);
		return Arrays.asList(bookArr);
	}

}
