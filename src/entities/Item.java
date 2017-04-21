package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the item database table.
 * 
 */
@Entity
@Table(name="item")
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
public class Item implements Serializable {
	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", buyPrice=" + buyPrice + ", currently=" + currently + ", description="
				+ description + ", ends=" + ends + ", first_Bid=" + first_Bid + ", latitude=" + latitude + ", location="
				+ location + ", longitude=" + longitude + ", name=" + name + ", number_of_Bids=" + number_of_Bids
				+ ", started=" + started + ", country=" + country + ", bids=" + bids + ", images=" + images + ", user="
				+ user + ", itemsHasCategories=" + itemsHasCategories + "]";
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="item_id")
	private Long itemId;

	@Column(name="buy_price", updatable=false)
	private Long buyPrice;

	private String currently;

	@Lob
	private String description;

	private Long ends;

	@Column(updatable=false)
	private String first_Bid;

	private double latitude;

	private String location;

	private double longitude;

	private String name;

	private int number_of_Bids;

	private Long started;
	
	private String country;

	//bi-directional many-to-one association to Bid
	@OneToMany(mappedBy="item", cascade = CascadeType.PERSIST)
	private List<Bid> bids;

	//bi-directional many-to-one association to Image
	@OneToMany(mappedBy="item")
	private List<Image> images;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="seller_id")
	private User user;

	//bi-directional many-to-one association to ItemsHasCategory
	@OneToMany(mappedBy="item", cascade = CascadeType.PERSIST)
	private List<ItemsHasCategory> itemsHasCategories;

	public Item() {
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getBuyPrice() {
		return this.buyPrice;
	}

	public void setBuyPrice(Long buyPrice) {
		this.buyPrice = buyPrice;
	}

	public String getCurrently() {
		return this.currently;
	}

	public void setCurrently(String currently) {
		this.currently = currently;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getEnds() {
		return this.ends;
	}

	public void setEnds(Long ends) {
		this.ends = ends;
	}

	public String getFirst_Bid() {
		return this.first_Bid;
	}

	public void setFirst_Bid(String first_Bid) {
		this.first_Bid = first_Bid;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber_of_Bids() {
		return this.number_of_Bids;
	}

	public void setNumber_of_Bids(int number_of_Bids) {
		this.number_of_Bids = number_of_Bids;
	}

	public Long getStarted() {
		return this.started;
	}

	public void setStarted(Long started) {
		this.started = started;
	}

	public List<Bid> getBids() {
		return this.bids;
	}

	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}

	public Bid addBid(Bid bid) {
		getBids().add(bid);
		bid.setItem(this);

		return bid;
	}

	public Bid removeBid(Bid bid) {
		getBids().remove(bid);
		bid.setItem(null);

		return bid;
	}

	public List<Image> getImages() {
		return this.images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public Image addImage(Image image) {
		getImages().add(image);
		image.setItem(this);

		return image;
	}

	public Image removeImage(Image image) {
		getImages().remove(image);
		image.setItem(null);

		return image;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ItemsHasCategory> getItemsHasCategories() {
		return this.itemsHasCategories;
	}

	public void setItemsHasCategories(List<ItemsHasCategory> itemsHasCategories) {
		this.itemsHasCategories = itemsHasCategories;
	}

	public ItemsHasCategory addItemsHasCategory(ItemsHasCategory itemsHasCategory) {
		getItemsHasCategories().add(itemsHasCategory);
		itemsHasCategory.setItem(this);

		return itemsHasCategory;
	}

	public ItemsHasCategory removeItemsHasCategory(ItemsHasCategory itemsHasCategory) {
		getItemsHasCategories().remove(itemsHasCategory);
		itemsHasCategory.setItem(null);

		return itemsHasCategory;
	}

}