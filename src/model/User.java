package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    
   
	@Override
	public String toString() {
		return "User [userID=" + userID + ", address=" + address + ", email=" + email + ", latitude=" + latitude
				+ ", longtitude=" + longtitude + ", name=" + name + ", password=" + password + ", phoneNumber="
				+ phoneNumber + ", surname=" + surname + ", username=" + username + ", afm=" + afm + ", country="
				+ country + "]";
	}

	@JsonIgnore
	private Long userID;

	private String address;

	private String email;

	private double latitude;

	private double longtitude;

	private String name;

	private String password;
	
	private Long phoneNumber;

	private String surname;

	private String username;
	
	private Long afm;
	
	private String country;
	
	private String location;
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@JsonIgnore
	private Boolean showAccept;
	@JsonIgnore
	private Boolean showDecline;
	@JsonIgnore
	private Boolean showInserted;
	@JsonIgnore
	private Boolean showDeclined;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getAfm() {
		return afm;
	}

	public void setAfm(Long afm) {
		this.afm = afm;
	}

	public User() {
	}

	@JsonIgnore
	public Long getUserID() {
		return this.userID;
	}

	@JsonIgnore
	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongtitude() {
		return this.longtitude;
	}

	public void setLongtitude(double longitude) {
		this.longtitude = longitude;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}