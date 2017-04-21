package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the bids database table.
 * 
 */
@Entity
@Table(name="bids")
@NamedQuery(name="Bid.findAll", query="SELECT b FROM Bid b")
public class Bid implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bid_id")
	private Long bidId;

	private String amount;

	private Long time;

	//bi-directional many-to-one association to Item
	@ManyToOne
	@JoinColumn(name="item_item_id")
	private Item item;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="bidder_id")
	private User user;

	public Bid() {
	}

	public Long getBidId() {
		return this.bidId;
	}

	public void setBidId(Long bidId) {
		this.bidId = bidId;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Long getTime() {
		return this.time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}