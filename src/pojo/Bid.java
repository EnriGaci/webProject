package pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Bid")
public class Bid implements Serializable{
	
	private Bidder bidder;
	
	private String Time;
	
	private String Amount;

	@XmlElement(name="Bidder")
	public Bidder getBidder() {
		return bidder;
	}

	public void setBidder(Bidder bidder) {
		this.bidder = bidder;
	}

	@XmlElement(name="Time")
	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	@XmlElement(name="Amount")
	public String getAmmount() {
		return Amount;
	}

	public void setAmmount(String amount) {
		Amount = amount;
	}
	
	@Override
	public String toString() {
		return "Bid [bidder=" + bidder + ", Time=" + Time + ", Amount=" + Amount + "]";
	}

}
