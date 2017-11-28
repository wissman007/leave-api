package fr.adventium.leave.api.leaveapi.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fr.adventium.leave.api.leaveapi.dao.repositories.LeaveRepository;
import fr.adventium.leave.api.leaveapi.entities.Leave;
import fr.adventium.leave.api.leaveapi.user.exceptions.LeaveNotSavedException;

@RestController
public class LeaveRsource {
	
	@Autowired
	private LeaveRepository leaveRepository;
	
	@GetMapping(path="/leaves")
	public List<Leave> retrieveLeaves(){
		return leaveRepository.findAll();
	}
	
	
	@PostMapping(path="/leaves")
	public ResponseEntity<Object> createLeave(@RequestBody Leave leave){
		Leave leaveSaved = leaveRepository.save(leave);
		
		URI location =		
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(leaveSaved.getId()).toUri();
		return ResponseEntity.created(location).build();
		
	}
	
	@GetMapping(path="/leaves/{id}")
	public Resource<Leave> retrieveLeave(@PathVariable int id) {
		Optional<Leave> leaveOption = this.leaveRepository.findById(id); 
		
		if(!leaveOption.isPresent()){
			throw new LeaveNotSavedException("id-"+id);
		}

		//Hateos
		// "all-users", SERVER_PATH + "users"
		 
		Resource<Leave> resource = new Resource<Leave>(leaveOption.get());
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveLeaves());
		resource.add(linkTo.withRel("all-leaves"));
		
		return resource ;
	}
	
}
