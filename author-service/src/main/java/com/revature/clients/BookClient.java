package com.revature.clients;

import java.util.List;

import com.revature.dto.BookDTO;

public interface BookClient {
	List<BookDTO> getBooksByAuthorId(int id);
}
