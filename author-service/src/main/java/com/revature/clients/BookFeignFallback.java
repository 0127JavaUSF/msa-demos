package com.revature.clients;

import java.util.List;

import org.springframework.stereotype.Component;

import com.revature.dto.BookDTO;

@Component
public class BookFeignFallback implements BookFeignClient {

	@Override
	public List<BookDTO> findBooksByAuthorId(int id) {
		return null;
	}
}
