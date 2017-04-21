package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the items_has_category database table.
 * 
 */
@Entity
@Table(name="items_has_category")
@NamedQuery(name="ItemsHasCategory.findAll", query="SELECT i FROM ItemsHasCategory i")
public class ItemsHasCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ItemsHasCategoryPK id;

	//bi-directional many-to-one association to Category
	@ManyToOne
	private Category category;

	//bi-directional many-to-one association to Item
	@ManyToOne
	private Item item;

	public ItemsHasCategory() {
	}

	public ItemsHasCategoryPK getId() {
		return this.id;
	}

	public void setId(ItemsHasCategoryPK id) {
		this.id = id;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}