package fr.adventium.leave.api.leaveapi.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Leave {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private Date beginDate;
	private Date endDate;
	private String type;
	
	private String description;
	
	
	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@JsonIgnore
	@JoinColumn(name="id_user")
	private User user;
	
	
	
	public Leave(Integer id, Date beginDate, Date endDate, String type, String description, User user) {
		super();
		this.id = id;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.type = type;
		this.description = description;
		this.user = user;
	}
	//	public User getUser() {
//		return user;
//	}
	public void setUser(User user) {
		this.user = user;
	}
	public Leave() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
