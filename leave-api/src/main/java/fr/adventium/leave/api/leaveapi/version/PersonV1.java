package fr.adventium.leave.api.leaveapi.version;

public class PersonV1 {
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;

	public PersonV1(String name) {
		super();
		this.name = name;
	}

	public PersonV1() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
