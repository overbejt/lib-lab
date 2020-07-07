package com.joverbeck.library.users;

import static java.util.Collections.emptyList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	// State variable
	private MyUserRepository myUserRepository;
	
	// Constructor
	public UserDetailServiceImpl(MyUserRepository myUserRepository) {
		this.myUserRepository = myUserRepository;
	}  // End of the Constructor
	
	/**
	 * Have to override this in order to connect the user repository with the 
	 * spring security Users list.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUser myUser = myUserRepository.findByUsername(username);
		if (myUser == null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(myUser.getUsername(), myUser.getPassword(), emptyList());
	}  // End of the 'loadUserByUsername' method
}  // End of the 'UserDetailServiceImpl' interface
