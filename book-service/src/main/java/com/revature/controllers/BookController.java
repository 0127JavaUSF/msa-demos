package com.revature.controllers;

import java.util.Map;

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

import com.revature.models.Book;
import com.revature.services.BookService;

@RestController
public class BookController {

	private BookService bookService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Book create(@RequestBody Book book) {
		return bookService.create(book);
	}

	@GetMapping("/{id}")
	public Book findBookById(@PathVariable int id) {
		return bookService.findById(id);
	}
	
	@GetMapping
	public Page<Book> findBookPaged(Pageable pageable) {
		return bookService.findBooks(pageable);
	}
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<String> handleClientError(HttpClientErrorException e) {
		return ResponseEntity
				.status(e.getStatusCode())
				.body(String.format("{\"message\": \"%s\"}", e.getMessage()));
	}
	
	@Autowired
	public BookController(BookService bookService) {
		super();
		this.bookService = bookService;
	}
}
