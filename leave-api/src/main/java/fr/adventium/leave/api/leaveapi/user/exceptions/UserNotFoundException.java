package fr.adventium.leave.api.leaveapi.user.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
	
	private String message;
	
	public UserNotFoundException(String message){
		this.message = "User not found with " + message;
	}
	
	
	public UserNotFoundException() {
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
