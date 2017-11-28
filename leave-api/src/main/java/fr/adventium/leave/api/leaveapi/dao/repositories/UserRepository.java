package fr.adventium.leave.api.leaveapi.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.adventium.leave.api.leaveapi.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{


	
	
}
