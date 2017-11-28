package fr.adventium.leave.api.leaveapi.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fr.adventium.leave.api.leaveapi.dao.repositories.LeaveRepository;
import fr.adventium.leave.api.leaveapi.dao.repositories.UserRepository;
import fr.adventium.leave.api.leaveapi.entities.Leave;
import fr.adventium.leave.api.leaveapi.entities.User;
import fr.adventium.leave.api.leaveapi.user.exceptions.UserNotFoundException;
import fr.adventium.leave.api.leaveapi.user.exceptions.UserNotSavedException;

@RestController
public class UserResource {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LeaveRepository leaveRepository;
	
	public UserResource(){
	
	}
	@CrossOrigin(origins="http://localhost:4200")
	@GetMapping(path="/users")
	public List<User> retrieveAllUsers(){
		return this.userRepository.findAll();
	}
	
	@GetMapping(path="/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = this.userRepository.findById(id); 
		
		if(!user.isPresent()){
			throw new UserNotFoundException("id-"+id);
		}

		//Hateos
		// "all-users", SERVER_PATH + "users"
		 
		Resource<User> resource = new Resource<User>(user.get());
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));
		
		return resource ;
	}
	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
		User savedUser = userRepository.save(user);
		if(savedUser.getId() == null){
			throw new UserNotSavedException("Bad request");
		}
		
		URI location =		
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
				
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id){
		
		userRepository.delete(userRepository.findById(id).get());
		
	}
	
	@GetMapping("/users/{id}/leaves")
	public List<Leave> retrieveAllLeaves(@PathVariable int id){
		Optional<User> user = this.userRepository.findById(id); 
		
		if(!user.isPresent()){
			throw new UserNotFoundException("id-"+id);
		}
		
		return user.get().getLeaves();
	}
	
	@PostMapping("/users/{id}/leaves")
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Leave leave){
		 Optional<User> userOptional = userRepository.findById(id);
		 if(!userOptional.isPresent()){
				throw new UserNotFoundException("id-"+id);
			}
		 
		 User user = userOptional.get();
		 
		 leave.setUser(user);
		 
		 leaveRepository.save(leave);
		
		URI location =		
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(leave.getId()).toUri();
		
		
		return ResponseEntity.created(location).build();
	}
	
	
}
