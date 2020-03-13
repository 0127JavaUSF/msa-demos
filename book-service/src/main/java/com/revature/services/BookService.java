package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.Book;
import com.revature.repositories.BookRepository;

@Service
public class BookService {

	private BookRepository bookRepository;

	public Book create(Book book) {
		return this.bookRepository.save(book);
	}

	public Page<Book> findBooks(Pageable pageable) {
		return this.bookRepository.findAll(pageable);
	}

	public Book findById(int id) {
		return bookRepository.findById(id).orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, 
					"Book with id " + id + " not found"));
	}

	@Autowired
	public BookService(BookRepository bookRepository) {
		super();
		this.bookRepository = bookRepository;
	}

	public List<Book> findBooksByAuthorId(int id) {
		return this.bookRepository.findBookByAuthorId(id);
	}

	void afterPropertiesSet() {
		System.out.println("Feign Client initialized");
	}
	
	public void deleteBooksByAuthorId(int authorId) {
		bookRepository.deleteByAuthorId(authorId);
	}
}
