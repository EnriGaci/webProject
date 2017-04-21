package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the items_has_category database table.
 * 
 */
@Embeddable
public class ItemsHasCategoryPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="items_has_category_id")
	private Long itemsHasCategoryId;

	@Column(name="item_item_id", insertable=false, updatable=false)
	private Long itemItemId;

	@Column(name="category_category_id", insertable=false, updatable=false)
	private Long categoryCategoryId;

	public ItemsHasCategoryPK() {
	}
	public Long getItemsHasCategoryId() {
		return this.itemsHasCategoryId;
	}
	public void setItemsHasCategoryId(Long itemsHasCategoryId) {
		this.itemsHasCategoryId = itemsHasCategoryId;
	}
	public Long getItemItemId() {
		return this.itemItemId;
	}
	public void setItemItemId(Long itemItemId) {
		this.itemItemId = itemItemId;
	}
	public Long getCategoryCategoryId() {
		return this.categoryCategoryId;
	}
	public void setCategoryCategoryId(Long categoryCategoryId) {
		this.categoryCategoryId = categoryCategoryId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ItemsHasCategoryPK)) {
			return false;
		}
		ItemsHasCategoryPK castOther = (ItemsHasCategoryPK)other;
		return 
			this.itemsHasCategoryId.equals(castOther.itemsHasCategoryId)
			&& this.itemItemId.equals(castOther.itemItemId)
			&& this.categoryCategoryId.equals(castOther.categoryCategoryId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.itemsHasCategoryId.hashCode();
		hash = hash * prime + this.itemItemId.hashCode();
		hash = hash * prime + this.categoryCategoryId.hashCode();
		
		return hash;
	}
}