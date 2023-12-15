package org.java.spring.controller;

import java.util.List;

import org.java.spring.pojo.db.Category;
import org.java.spring.pojo.serv.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class CategoryController {
	
	@Autowired
	CategoryService catService;
	
	@GetMapping("/categories")
	public String ingedientsAll(Model model) {
		
		List <Category> categories = catService.findAll();
		
		model.addAttribute("categories", categories);
		
		return("categories");
	}
	
	@GetMapping("/create-category")
	public String createIngredient(Model model) {
		
		Category category = new Category();
		model.addAttribute("category", category);
		return "category-form";
	}
	
	@PostMapping("/create-category")
	public String storeIngredient(Model model,
		@Valid @ModelAttribute Category category,
		BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			
//			System.out.println(bindingResult);
			model.addAttribute("category", category);
			return "category-form";
		}
		
		try {
			
			catService.save(category);
		} catch(Exception e) {
			
			bindingResult.addError(new FieldError("category", "name", category.getName(), false, null, null, "Name must be unique"));
			model.addAttribute("category", category);
			return "category-form";
		}

		return("redirect:/categories");
	}
	
	@PostMapping("/delete/category/{id}")
	public String delete(@PathVariable int id) {
		
		Category c = catService.findById(id);
		c.clearPhotos();
		catService.save(c);
		catService.delete(c);
		
		return "redirect:/categories";
	}

}
