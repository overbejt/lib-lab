package com.joverbeck.library.users;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {
	// State variables
	private MyUserRepository myUserRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// Constructor
	public UserController(MyUserRepository myUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.myUserRepository = myUserRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}  // End of the Constructor
	
	/**
	 * This is the method that will handle when a user tries to sign up.
	 * It's not very secure yet.  But this is not a perfect schema.
	 * 
	 * @param user The user object from the post body.
	 */
	@PostMapping("/sign-up")
	public ResponseEntity<?> signup (@RequestBody MyUser user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		myUserRepository.save(user);
		System.out.println("=== New user was created ===");
		return ResponseEntity.ok("{}");
	}  // End of the 'signup' method
	
}  // End of the 'UserController' class
