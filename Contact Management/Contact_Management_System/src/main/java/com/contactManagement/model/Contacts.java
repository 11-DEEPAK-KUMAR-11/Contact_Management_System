package com.contactManagement.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
public class Contacts {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cid;
	
	@Column(name = "fullname", length = 50)
	@NotBlank(message = "full nmae cannot be blank")
	private String fullName;
	
	@Column(name = "email", length = 50)
	@NotBlank(message ="email cannot be blank")
	@Email(message="email format is not correct")
	private String email;
	
	@Column(name = "mobile", length=10)
	@NotBlank(message = "mobile num cannot be blank")
	@Pattern(regexp ="[1-9][0-9]{9}", message ="moblie num format is wrong")
	private String mobile;
	
	@Column(name = "dob")
	@NotNull(message ="dob cannot be blank")
	@Past(message="bod format is not correct")
	@DateTimeFormat(iso=ISO.DATE)
	private Date dateOfBirth;
	
	
		
	@Override
	public int hashCode() {
		return Objects.hash(cid, dateOfBirth, email, fullName, mobile);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contacts other = (Contacts) obj;
		return Objects.equals(cid, other.cid) && Objects.equals(dateOfBirth, other.dateOfBirth)
				&& Objects.equals(email, other.email) && Objects.equals(fullName, other.fullName)
				&& Objects.equals(mobile, other.mobile);
	}

	public int compareTo(Contacts o) {
		return this.cid.compareTo(o.cid);
	}

	
	
	public Integer getCid() {
		return cid;
	}


	public void setCid(Integer cid) {
		this.cid = cid;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public Date getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
}
