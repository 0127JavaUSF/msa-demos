package com.revature.clients;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.revature.dto.BookDTO;

public interface BookClient {
	@GetMapping("/author/{id}")
	public List<BookDTO> findBooksByAuthorId(@PathVariable int id);
}
