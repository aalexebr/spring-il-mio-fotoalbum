package org.java.spring.pojo.db;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, unique = true, length = 32)
	@NotBlank(message = "Title is mandatory")
	@NotNull(message = "Title cannot be null")
	@NotEmpty(message = "Title cannot be null")
	@Length(min = 3 ,max = 32, message = "Title must be max 32 chars")
	private String name;
	
	@ManyToMany(mappedBy = "categories")
	@JsonIgnore
	private List<Photo> photos;
	
	public Category() {}
	
	public Category(String name) {
		setName(name);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	
	public void clearPhotos() {
		for(Photo ph : getPhotos()) {
			ph.clearCategories(this);
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
	
}
