package com.joverbeck.library.books;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v2/books")
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
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getBookById(@PathVariable long id) {
		System.out.println("=== Retrieving book: " + id + " ===");
		try {
			// Get the book
			Book resultBook = bookRepository.findById(id).get();		
			// Otherwise, Send the book back with OK status
			return new ResponseEntity<Book>(resultBook, HttpStatus.OK);
		} catch (Exception e) {
			// Send back not found
			return ResponseEntity.notFound().build();
		}
	}  // End of the 'getBookById' method
	
	/**
	 * This is the method that will grab all of the books from the repository 
	 * and send them back as a list.
	 * 
	 * @return A list of all the books in the repository.
	 */
	@GetMapping("")
	public List<Book> getAllBooks() {
		System.out.println("=== Retrieving all books ===");
		return bookRepository.findAll();
	}  // End of the 'getAllBooks' method
	
	
	/**
	 * This is the method that will add a new book into the repository.
	 * 
	 * @param book The new book that is supposed to be added to the repository.
	 */
	@PostMapping("")
	public ResponseEntity<?> addBook(@RequestBody Book book) {
		System.out.println("=== Book saved ===");
		try {
			bookRepository.save(book);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}  // End of the 'addBook' method
	
	/**
	 * This is the method that will update a book.  
	 * 
	 * @param id The id of the book.
	 * @param book The book object that needs to be updated.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<?> updateBook(@PathVariable long id, @RequestBody Book book) {
		
		System.out.println("=== Updating book: " + id + " ===");
		// Check if adding a new book
		if (id < 0) {
			// Reset the id
			book.setId(null);
			// Add the book
			addBook(book);
			// Send back 200 response
			return ResponseEntity.ok().build();
		}
		// Otherwise, update the book
		Book originalBook;
		try {
			// Get the book from the repository
			originalBook = bookRepository.findById(id).get();
			// Set the id to the new book object
			book.setId(originalBook.getId());
			// Put the new book into the repository
			bookRepository.save(book);
			// Send back 200 response
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			// When it does not exist, send back a bad request with error messaged
			return ResponseEntity.notFound().build();
		}				
	}  // End of the 'updateBook' method
	
	/**
	 * This is the method that will delete a book.
	 * 
	 * @param id The id of the book.
	 * @param book The book object that needs to be deleted.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable long id) {
		
		System.out.println("=== Deleting book: " + id + " ===");
		
		Book originalBook;
		try {
			// Get the book from the repository
			originalBook = bookRepository.findById(id).get();
			// Delete the book
			bookRepository.delete(originalBook);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			// When it does exist, send back a bad request with error message
			return ResponseEntity.notFound().build();
		}			
	}  // End of the 'deleteBook' method
	
}  // End of the 'BookController' class
