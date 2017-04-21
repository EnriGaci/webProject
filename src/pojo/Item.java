package pojo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javax.xml.bind.annotation.XmlAttribute;

@XmlRootElement(name = "Item")
public class Item implements Serializable{

	@XmlAttribute
	private Long ItemID;
	
	private String Name;
	
	private List<String> Categories;
	
	private String Currently;
	
	private String First_Bid;
	
	private Integer Number_of_Bids;
	
	private Bids bids;
	
	private Location location;
	
	private String Country;
	
	private String Started;
	
	private String Ends;
	
	private Seller seller;
	
	private String Description;

	@XmlAttribute(name="ItemID")
	public Long getItemID() {
		return ItemID;
	}

	public void setItemID(Long itemID) {
		ItemID = itemID;
	}

	@XmlElement(name="Name")
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	@XmlElement(name="Category")
	public List<String> getCategories() {
		return Categories;
	}

	public void setCategories(List<String> categories) {
		Categories = categories;
	}

	@XmlElement(name="Currently")
	public String getCurrently() {
		return Currently;
	}

	public void setCurrently(String currently) {
		Currently = currently;
	}

	@XmlElement(name="First_Bid")
	public String getFirst_Bid() {
		return First_Bid;
	}

	public void setFirst_Bid(String first_Bid) {
		First_Bid = first_Bid;
	}

	@XmlElement(name="Number_of_Bids")
	public Integer getNumber_of_Bids() {
		return Number_of_Bids;
	}

	public void setNumber_of_Bids(Integer number_of_Bids) {
		Number_of_Bids = number_of_Bids;
	}

	@XmlElement(name="Bids")
	public Bids getBids() {
		return bids;
	}

	public void setBids(Bids bids) {
		this.bids = bids;
	}

	@XmlElement(name="Location")
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@XmlElement(name="Country")
	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	@XmlElement(name="Started")
	public String getStarted() {
		return Started;
	}

	public void setStarted(String started) {
		Started = started;
	}

	@XmlElement(name="Ends")
	public String getEnds() {
		return Ends;
	}

	public void setEnds(String ends) {
		Ends = ends;
	}

	@XmlElement(name="Seller")
	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	@XmlElement(name="Description")
	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	@Override
	public String toString() {
		return "Item [ItemID=" + "\n" + ItemID + "\n" + ", Name=" + "\n" + Name + "\n" + ", Categories=" + "\n" + Categories + "\n" + ", Currently=" + "\n" + Currently
				+ "\n" + ", First_Bid=" + "\n" + First_Bid + "\n" + ", Number_of_Bids=" + "\n" + Number_of_Bids + "\n" + ", bids=" + "\n" + bids + "\n" + ", location="
				+ "\n" + location + "\n" + ", Country=" + "\n" + Country + "\n" + ", Started=" + "\n" + Started + "\n" + ", Ends=" + "\n" + Ends + "\n" + ", seller=" + "\n" + seller
				+ "\n" + ", Description=" + "\n" + Description + "\n" + "]";
	}
	
	
	
}
