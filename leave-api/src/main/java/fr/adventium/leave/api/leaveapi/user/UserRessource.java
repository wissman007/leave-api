package fr.adventium.leave.api.leaveapi.user;

import java.net.URI;
import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fr.adventium.leave.api.leaveapi.user.exceptions.UserNotFoundException;
import fr.adventium.leave.api.leaveapi.user.exceptions.UserNotSavedException;

@RestController
public class UserRessource {
	
	@Autowired
	private UserDaoService userDaoService;
	
	public UserRessource(){
	
	}
	
	@GetMapping(path="/users")
	public List<User> retrieveAllUsers(){
		return this.userDaoService.findAll();
	}
	
	@GetMapping(path="/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id) {
		User user = this.userDaoService.findOne(id); 
		
		if(user==null){
			throw new UserNotFoundException("id-"+id);
		}

		//Hateos
		// "all-users", SERVER_PATH + "users"
		 
		Resource<User> resource = new Resource<User>(user);
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));
		
		return resource ;
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
		User savedUser = userDaoService.save(user);
		if(savedUser.getId() == null){
			throw new UserNotSavedException("Bad request");
		}
		
		URI location =		
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
				
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id){
		
		User deletedUser = this.userDaoService.deleteUser(id);
		if(deletedUser == null){
			throw new UserNotFoundException("id-"+id);
		}	
		
	}
}
