package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.clients.BookClient;
import com.revature.dto.BookDTO;
import com.revature.models.Author;
import com.revature.repository.AuthorRepository;

@Service
public class AuthorService {
	private AuthorRepository authorRepository;
	private BookClient bookClient;

	@Autowired
	public AuthorService(AuthorRepository authorRepository, BookClient bookClient) {
		this.authorRepository = authorRepository;
		this.bookClient = bookClient;
	}

	public Author create(Author author) {
		return authorRepository.save(author);
	}

	public Author findById(int id) {
		Author author = authorRepository.findById(id)
				.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
		
		// Attempt to retrieve books for this author
		List<BookDTO> books = this.bookClient.findBooksByAuthorId(author.getId());
		author.setBooksWritten(books);
		
		return author;
	}

	public Page<Author> findAll(Pageable pageable) {
		return authorRepository.findAll(pageable);
	}
}
