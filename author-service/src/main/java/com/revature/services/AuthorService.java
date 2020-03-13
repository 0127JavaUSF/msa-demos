package com.revature.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.clients.BookClient;
import com.revature.dto.BookDTO;
import com.revature.messengers.SNSMessenger;
import com.revature.models.Author;
import com.revature.repository.AuthorRepository;

@Service
public class AuthorService {
	private AuthorRepository authorRepository;
	private BookClient bookClient;
	private SNSMessenger snsMessenger;

	@Autowired
	public AuthorService(AuthorRepository authorRepository,
			BookClient bookClient, SNSMessenger snsMessenger) {
		this.authorRepository = authorRepository;
		this.bookClient = bookClient;
		this.snsMessenger = snsMessenger;
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

	@Transactional
	public void deleteById(int id) {
		authorRepository.deleteById(id);
		// Send a message to the book service to delete all books written
		// by that author
		snsMessenger.sendDeleteBooksMessage(id);
		// send a message
	}
}
