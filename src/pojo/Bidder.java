package pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="Bidder")
public class Bidder implements Serializable {
	
	@XmlAttribute
	private Long Rating;
	
	@XmlAttribute
	private String UserID;
	
	private String Location;
	
	@Override
	public String toString() {
		return "Bidder [Rating=" + Rating + ", UserID=" + UserID + ", Location=" + Location + ", Country=" + Country
				+ "]";
	}

	private String Country;

	@XmlAttribute(name="Rating")
	public Long getRating() {
		return Rating;
	}

	public void setRating(Long rating) {
		Rating = rating;
	}

	@XmlAttribute(name="UserID")
	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	@XmlElement(name="Location")
	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	@XmlElement(name="Country")
	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

}
