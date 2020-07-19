package com.joverbeck.library.users;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v2/users")
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
		// Check if the user does not exist
		if (myUserRepository.findByUsername(user.getUsername()) == null) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			myUserRepository.save(user);
			System.out.println("=== New user was created ===");
			return ResponseEntity.ok().build();
		}
		// When it does not exist, send back a bad request with error message
		String errorMsg = "{\"message\" : \"Username already in use.\"}";
		return ResponseEntity.badRequest().body(errorMsg);
	}  // End of the 'signup' method
	
}  // End of the 'UserController' class
