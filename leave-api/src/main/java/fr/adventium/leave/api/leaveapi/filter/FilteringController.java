package fr.adventium.leave.api.leaveapi.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import fr.adventium.leave.api.leaveapi.entities.User;

@RestController
public class FilteringController {
	
	@GetMapping("/filtring-list-dynamic")
	// filter user1
	public MappingJacksonValue filterUsers(){
		
		User user = new User();
		User user1 = new User();
		List<User> users = new ArrayList<User>(); 
		
		
		users.add(user);
		users.add(user1);
		
//		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name");
//		
//		FilterProvider filters = new SimpleFilterProvider().addFilter("userFilter", filter);
//		
//		MappingJacksonValue mapping = new MappingJacksonValue(users);
//		mapping.setFilters(filters);
		
		String[] filterName = {"name"}; 
		return getMappingFilter("userFilter",filterName  , users );
		
	}
	
	private MappingJacksonValue getMappingFilter(String filterName,  String[] filters, Object elements){
		
			SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(filters);
		
			FilterProvider filtersProvider = new SimpleFilterProvider().addFilter(filterName, filter);
		
			MappingJacksonValue mapping = new MappingJacksonValue(elements);
			
			mapping.setFilters(filtersProvider);
			
			return mapping;
		
	}
}
