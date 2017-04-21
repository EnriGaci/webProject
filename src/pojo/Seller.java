package pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Seller")
public class Seller implements Serializable{
	
	@XmlAttribute
	private Long Rating;
	
	@XmlAttribute
	private String UserID;

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
	
	@Override
	public String toString() {
		return "Seller [Rating=" + Rating + ", UserID=" + UserID + "]";
	}

}
