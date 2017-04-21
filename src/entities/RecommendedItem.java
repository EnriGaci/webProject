package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the recommendedItems database table.
 * 
 */
@Entity
@Table(name="recommendedItems")
@NamedQuery(name="RecommendedItem.findAll", query="SELECT r FROM RecommendedItem r")
public class RecommendedItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long recId;

	private Long itemId;

	private Long userId;

	public RecommendedItem() {
	}

	public Long getRecId() {
		return this.recId;
	}

	public void setRecId(Long recId) {
		this.recId = recId;
	}

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}