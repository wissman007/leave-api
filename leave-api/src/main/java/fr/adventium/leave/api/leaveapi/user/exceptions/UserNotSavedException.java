package fr.adventium.leave.api.leaveapi.user.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotSavedException extends RuntimeException {
	
	private String message;
	
	public UserNotSavedException(String message){
		this.message = "User not Saved " + message;
	}
	
	
	public UserNotSavedException() {
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
