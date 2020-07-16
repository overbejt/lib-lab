package com.joverbeck.library.books;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/books")
public class BookController {
	
	// Declaring a state variable to hold the book repository object
	private BookRepository bookRepository;
	
	/**
	 * Defining a constructor so that I can initialize the BookRepository object.
	 * 
	 * @param bookRepository
	 */
	public BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}  // End of the Constructor
	
	/**
	 * This is the method that will grab all of the books from the repository 
	 * and send them back as a list.
	 * 
	 * @return A list of all the books in the repository.
	 */
	@GetMapping()
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}  // End of the 'getAllBooks' method
	
	/**
	 * This is the method that will add a new book into the repository.
	 * 
	 * @param book The new book that is supposed to be added to the repository.
	 */
	@PostMapping()
	public void addBook(@RequestBody Book book) {
		bookRepository.save(book);
	}  // End of the 'addBook' method
	
	/**
	 * This is the method that will update a book.  
	 * 
	 * @param id The id of the book.
	 * @param book The book object that needs to be updated.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<?> updateBook(@PathVariable long id, @RequestBody Book book) {
		// Get the book from the repository
		Book originalBook = bookRepository.findById(id).get();
		try {
			// Verify that it is not null, otherwise send an error message back
			Assert.notNull(originalBook, "Book Not Found!");
		} catch (Exception e) {
			// When it does exist, send back a bad request with error message
			String errorMsg = "{\"message\" : \"Book Not Found.\"}";
			return ResponseEntity.badRequest().body(errorMsg);
		}
		// Set the id to the new book object
		book.setId(originalBook.getId());
		// Put the new book into the repository
		bookRepository.save(book);
		return ResponseEntity.ok("{}");
	}  // End of the 'updateBook' method
	
	/**
	 * This is the method that will delete a book.
	 * 
	 * @param id The id of the book.
	 * @param book The book object that needs to be deleted.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable long id, @RequestBody Book book) {
		// Get the book from the repository
		Book originalBook = bookRepository.findById(id).get();
		try {
			// Verify that it is not null, otherwise send an error message back
			Assert.notNull(originalBook, "Book Not Found!");
		} catch (Exception e) {
			// When it does exist, send back a bad request with error message
			String errorMsg = "{\"message\" : \"Book Not Found.\"}";
			return ResponseEntity.badRequest().body(errorMsg);
		}
		// Delete the book
		bookRepository.delete(originalBook);
		return ResponseEntity.ok("{}");	
	}  // End of the 'deleteBook' method
	
}  // End of the 'BookController' class
