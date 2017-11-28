package fr.adventium.leave.api.leaveapi.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(description="User Model")
//@JsonIgnoreProperties(value={"id"})
//@JsonFilter(value="userFilter")
@Entity
public class User {
	@Id
	@GeneratedValue
	private Integer id;
	@Size(min=2, message="Name should have at least 2 characters")
	private String firstName;
	
	private String lastName;
	@Past
	@ApiModelProperty(notes="Birth date can't be in the past")
//	@JsonIgnore
	private Date birthDate;
	
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	
	private List<Leave> leaves; 
	
	private String email;
	private String password;
	
	

	public User(String firstName, String lastName, Date birthDate, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.email = email;
		this.password = password;
	}

	public User(Integer id, String firstName, String lastName, Date birthDate, List<Leave> leaves) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.leaves = leaves;
	}

	public User(Integer id, String firstName, String lastName, Date birthDate) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public List<Leave> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<Leave> leaves) {
		this.leaves = leaves;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
