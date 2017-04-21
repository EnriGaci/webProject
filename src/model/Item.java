package model;

import java.util.List;

public class Item {
	
	Long id;
	
	String name;
	
	Long price;
	
	String description;
	
	List<String> images;

	List<String> categories;
	
	String locationName;
	
	double longitude;
	
	double latitude;
	
	String firstBid;
	
	String currently;
	
	Long endsDate;
	
	String userId;
	
	int number_of_Bids;
	
	Long started;
	
	String seller;
	
	List<Bid> bids;
	
	String country;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setPrice(Long price) {
		this.price = price;
	}
	
	public List<Bid> getBids() {
		return bids;
	}

	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public Long getStarted() {
		return started;
	}

	public void setStarted(Long started) {
		this.started = started;
	}

	public int getNumber_of_Bids() {
		return number_of_Bids;
	}

	public void setNumber_of_Bids(int number_of_Bids) {
		this.number_of_Bids = number_of_Bids;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getEndsDate() {
		return endsDate;
	}

	public void setEndsDate(Long endsDate) {
		this.endsDate = endsDate;
	}

	public String getCurrently() {
		return currently;
	}

	public void setCurrently(String currently) {
		this.currently = currently;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description + ", images="
				+ images + ", categories=" + categories + ", country="  + ", locationName=" + locationName
				+ ", longitude=" + longitude + ", latitude=" + latitude + ", firstBid=" + firstBid + ", currently="
				+ currently + ", endsDate=" + endsDate + ", userId=" + userId + ", number_of_Bids=" + number_of_Bids
				+ ", started=" + started + ", seller=" + seller + ", bids=" + bids + "]";
	}
	
	public String getFirstBid() {
		return firstBid;
	}

	public void setFirstBid(String firstBid) {
		this.firstBid = firstBid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longtitude) {
		this.longitude = longtitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
}
