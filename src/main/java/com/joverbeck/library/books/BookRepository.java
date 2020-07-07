package com.joverbeck.library.books;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
	//  I can specify different search methods in here
}  // End of the 'BookRepository' interface
