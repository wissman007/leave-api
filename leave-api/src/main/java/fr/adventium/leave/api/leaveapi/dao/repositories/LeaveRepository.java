package fr.adventium.leave.api.leaveapi.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.adventium.leave.api.leaveapi.entities.Leave;

public interface LeaveRepository extends JpaRepository<Leave, Integer>{

}
