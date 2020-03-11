package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.Author;
import com.revature.services.AuthorService;

@RestController
public class AuthorController {
	
	private AuthorService authorService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Author create(@RequestBody Author author) {
		return authorService.create(author);
	}
	
	@GetMapping("/{id}")
	public Author getAuthor(@PathVariable int id) {
		return authorService.findById(id);
	}
	
	@GetMapping
	public Page<Author> getAuthors(Pageable pageable) {
		return authorService.findAll(pageable);
	}
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<String> handleClientError(HttpClientErrorException e) {
		return ResponseEntity
			.status(e.getStatusCode())
			.body("{\"message\": \"" + e.getMessage() + "\"}");
	}
	
	@Autowired
	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}
}
