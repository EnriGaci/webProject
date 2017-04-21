package pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "Bids")
public class Bids implements Serializable {
	
	private List<Bid> bids;
	
	@XmlElement(name = "Bid")
	public List<Bid> getBids() {
		if (null==bids) {
			bids = new ArrayList<Bid>();
		}
		return bids;
	}

	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}

	@Override
	public String toString() {
		return "Bids [bids=" + bids + "]";
	}

}
