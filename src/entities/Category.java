package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table(name="category")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="category_id")
	private Long categoryId;

	private String name;

	//bi-directional many-to-one association to ItemsHasCategory
	@OneToMany(mappedBy="category", cascade = CascadeType.PERSIST)
	private List<ItemsHasCategory> itemsHasCategories;

	public Category() {
	}

	public Long getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ItemsHasCategory> getItemsHasCategories() {
		return this.itemsHasCategories;
	}

	public void setItemsHasCategories(List<ItemsHasCategory> itemsHasCategories) {
		this.itemsHasCategories = itemsHasCategories;
	}

	public ItemsHasCategory addItemsHasCategory(ItemsHasCategory itemsHasCategory) {
		getItemsHasCategories().add(itemsHasCategory);
		itemsHasCategory.setCategory(this);

		return itemsHasCategory;
	}

	public ItemsHasCategory removeItemsHasCategory(ItemsHasCategory itemsHasCategory) {
		getItemsHasCategories().remove(itemsHasCategory);
		itemsHasCategory.setCategory(null);

		return itemsHasCategory;
	}

}