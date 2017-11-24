package fr.adventium.leave.api.leaveapi.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	
	private static List<User> users = new ArrayList<User>();
	private static Integer usersCount = users.size(); 
	static {
		users.add(new User(1,"Wissem", new Date()));
		users.add(new User(1,"Mmo", new Date()));
		users.add(new User(1,"Balla", new Date()));
	}

	
	public List<User> findAll(){
		return users;
	}
	
	public User save(User user){
		if(user.getId()==null){
			user.setId(++usersCount);
		}
		
		if(user.getId()==5){
			user.setId(null);
		}
		
		users.add(user);
		
		return user;
	}
	
	
	public User findOne(int id){
		
		for (User user: users){
			if(user.getId()==id){
				return user;
			}
		}
		return null;
		
	}
	public User deleteUser(int id){
		User deletedUser = null;
		for (User user: users){
			if (user.getId()==id){
				
				deletedUser = user;
				users.remove(user.getId());
			
			}
		}
		
		return deletedUser;
	}
	
}
