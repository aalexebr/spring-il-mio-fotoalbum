package org.java.spring.pojo.db;

import java.util.Arrays;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Photo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 32)
	@NotBlank(message = "Title is mandatory")
	@NotNull(message = "Title cannot be null")
	@NotEmpty(message = "Title cannot be null")
	@Length(min = 2 ,max = 32, message = "Title must be max 32 chars")
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Column(columnDefinition = "TEXT")
	private String url;
	
	@Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
	private boolean visible;
	
	@ManyToMany
	private List<Category> categories;
	
	public Photo() {}
	
	public Photo(String title, String url, String description, boolean visible, Category...categories) {
		setTitle(title);
		setUrl(url);
		setDescription(description);
		setVisible(visible);
		setCategories(categories);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public void setCategories(Category... categories) {
		this.setCategories(Arrays.asList(categories));
	}
	
	public void clearCategories(Category... categories) {
		for(Category cat : categories) {
			getCategories().remove(cat);
		}
	}
	
	public void clearCategories() {
		getCategories().clear();
	}
	
	@Override
	public String toString() {

		return getTitle()+" - " + getDescription() + " - " +isVisible();
	}

}
