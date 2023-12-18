package org.java.spring.pojo.serv;

import java.util.ArrayList;
import java.util.List;

import org.java.spring.pojo.db.Category;
import org.java.spring.pojo.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	public List<Category> findAll(){
		return categoryRepo.findAll();
	}
	
	public Category findById(int id) {
		return categoryRepo.findById(id).orElse(null);
	}
	
	public Category findByName(String name) {
		return categoryRepo.findByName(name);
	}
	
	public void save(Category category) {
		categoryRepo.save(category);
	}
	
	public void delete(Category category) {
		
		categoryRepo.delete(category);
	}
	
	public void deleteById(int id) {
		
		categoryRepo.deleteById(id);
	}
	
	public List<Category> ingredientsById(List<Integer> ids){
		List<Category> categories = new ArrayList<>();
		for(Integer id : ids) {
			Category ing = this.findById(id);
			if(ing != null) categories.add(ing);
			
		}
		return categories;
	}
	
	
}
