package com.joverbeck.library;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joverbeck.library.books.Book;

@RestController
public class MyRestController {

	@GetMapping("/api")
	public String home() {
		return "Hello Muggles from the REST Controller!";
	}  // End of the 'home' method
	
	@GetMapping("/api/book1")
	public Book getBook() {
		Book book = new Book(12L, "White Fangs", 1234567891011L);
		return book;
	}  // End of the 'getBook' method
	
}  // End of 'MyRestController' class
