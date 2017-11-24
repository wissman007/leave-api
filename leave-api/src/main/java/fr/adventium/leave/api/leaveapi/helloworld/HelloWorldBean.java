package fr.adventium.leave.api.leaveapi.helloworld;

public class HelloWorldBean {
	private String message;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HelloWorldBean(String message){
		this.message = message;
	}

}
