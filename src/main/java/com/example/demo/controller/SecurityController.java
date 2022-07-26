package com.example.demo.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins="http://localhost:4200")
public class SecurityController {

	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(value = "/find")
	public ResponseEntity<Object> findUser(@RequestParam(required = false) String username) {
		User newUser = userRepository.findByUserName(username);
		if (newUser == null) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@PostMapping(value = "/add")
	public ResponseEntity<String> saveNewAccount(@RequestBody User newAccount) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User newUser = userRepository.findByUserName(newAccount.getUserName());
		if (newUser == null) {
			String encodedPassword = passwordEncoder.encode(newAccount.getPassword());
			newAccount.setPassword(encodedPassword);
			userService.save(newAccount);
			return new ResponseEntity<>("create new user", HttpStatus.CREATED);
		}
		return new ResponseEntity<>("fuckk u", HttpStatus.NOT_ACCEPTABLE);
	}
	
	@GetMapping("/show")
	public List<User> showUser() {
		return (List<User>) userRepository.findAll();
	}
	
}
