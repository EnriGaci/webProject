package JAXBModel;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Item")
public class Item {
	
	String name;
	
	Long ItemID;
	
	List<String> categories;
	
	String currently;
	
	String firstBid;
	
	int numberOfBids;
	
	String started;
	
	String ends;
	
	List<Bid> bids;
	
	Seller seller;
	
	Location location;
	
	String description;
	
	String country;
	
	@XmlElement(name="Country")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@XmlElement(name="Description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement(name="Location")
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@XmlElement(name="Seller")
	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	@XmlElement(name="Bids")
	public List<Bid> getBids() {
		return bids;
	}

	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}

	@XmlElement(name="Currently")
	public String getCurrently() {
		return currently;
	}

	public void setCurrently(String currently) {
		this.currently = currently;
	}

	@XmlElement(name="First_Bid")
	public String getFirstBid() {
		return firstBid;
	}

	public void setFirstBid(String firstBid) {
		this.firstBid = firstBid;
	}

	@XmlElement(name="Number_of_Bids")
	public int getNumberOfBids() {
		return numberOfBids;
	}

	public void setNumberOfBids(int numberOfBids) {
		this.numberOfBids = numberOfBids;
	}

	@XmlElement(name="Stared")
	public String getStarted() {
		return started;
	}

	public void setStarted(String started) {
		this.started = started;
	}

	@XmlElement(name="Ends")
	public String getEnds() {
		return ends;
	}

	public void setEnds(String ends) {
		this.ends = ends;
	}

	@XmlAttribute
	public Long getId() {
		return ItemID;
	}

	public void setId(Long id) {
		this.ItemID = id;
	}

	@XmlElement(name="Name")
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	@XmlElement(name="Category")
	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

}
