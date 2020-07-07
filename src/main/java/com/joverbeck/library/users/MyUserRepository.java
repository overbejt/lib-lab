package com.joverbeck.library.users;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This is an interface to create a repository of user objects.  
 *
 */
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
	// Providing a way to filter the users
	MyUser findByUsername(String username);
}  // End of the 'MyUserRepository' interface
