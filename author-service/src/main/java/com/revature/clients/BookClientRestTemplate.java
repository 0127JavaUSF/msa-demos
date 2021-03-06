package com.revature.clients;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.revature.dto.BookDTO;


/**
 * Functions as a client to the BookService implemented using Spring RestTemplates.
 * 
 * RestTemplate is a tool for sending HTTP requests (generally REST requests) from 
 * a Java application.  The basic functionality is similar to a fetch request or
 * an HttpClient request in JavaScript/Angular.
 * 
 * In practice, this is not the tool we would use for interservice communication
 * in a Microservices architecture.  Instead, we would use Feign - which is what
 * we will use in a later example.
 *
 */
@Component
@Profile("resttemplate")
public class BookClientRestTemplate implements BookClient {

	@Override
	public List<BookDTO> findBooksByAuthorId(int id) {
		RestTemplate template = new RestTemplate();
		String url = "http://localhost:8080/books/author/" + id;
		BookDTO[] bookArr = template.getForObject(url, BookDTO[].class);
		return Arrays.asList(bookArr);
	}

}
