package fr.adventium.leave.api.leaveapi.user.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LeaveNotSavedException extends RuntimeException {
	
	private String message;
	
	public LeaveNotSavedException(String message){
		this.message = "Leave not Saved " + message;
	}
	
	
	public LeaveNotSavedException() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
