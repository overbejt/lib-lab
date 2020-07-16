package com.joverbeck.library.books;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {
	
	// State variables
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String author;
	private String ISBN;
	
	// Default Constructor
	public Book() {
		// Default Constructor
	}  // End of the default Constructor
	
	// Overloaded Constructor 
	public Book(Long id, String title, String author, String ISBN) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.ISBN = ISBN;
	}  // End of the overloaded Constructor
	
	/**
	 * This is the method that will get the id of this book.
	 * 
	 * @return The id of this book.
	 */
	public Long getId() {
		return id;
	}  // End of the 'getId' method
	
	/**
	 * This is the method that will set the id of the book.
	 * 
	 * @param id The new id for this book.
	 */
	public void setId(Long id) {
		this.id = id;
	}  // End of the 'setId' method
	
	/**
	 * This is the method that will get the title of the book.
	 * 
	 * @return The title of the book.
	 */
	public String getTitle() {
		return title;
	}  // End of the 'getTitle' method
	
	/**
	 * This is the method that will set the title for this book.
	 * 
	 * @param title The title of this book.
	 */
	public void setTitle(String title) {
		this.title = title;
	}  // End of the 'setTitle' book
	
	/**
	 * This is the method that will get the author for this book.
	 * 
	 * @return The author of this book.
	 */
	public String getAuthor() {
		return author;
	}  // End of the 'getAuthor' method
	
	/**
	 * This is the method that will set the author for this book.
	 * 
	 * @param author The author of this book.
	 */
	public void setAuthor(String author) {
		this.author = author;
	}  // End of the 'setAuthor' method
	
	/**
	 * This is the method that will get the ISBN number for this book.
	 * 
	 * @return  The ISBN for this book.
	 */
	public String getISBN() {
		return ISBN;
	}  // End of the 'getISBN' method
	
	/**
	 * This is the method that will set the ISBN for this book.
	 * 
	 * @param ISBN  The new ISBN for this book.
	 */
	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}  // End of the 'setISBN' method 
	
	/**
	 * This is the overloaded toString method.  
	 */
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", ISBN=" + ISBN + "]";
	}  // End of the 'toString' method
	
}  // End of the 'Book' class
