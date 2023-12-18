package org.java.spring.controller;

import java.util.List;

import org.java.spring.pojo.db.Category;
import org.java.spring.pojo.db.Photo;
import org.java.spring.pojo.serv.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CategoryRestController {

	@Autowired
	CategoryService catService;
	
	@GetMapping("categories")
	public ResponseEntity<List<Category>> index(@RequestParam(required=false) String title){
		
		List<Category> cats = catService.findAll();
		
		if(cats.size() == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(cats, HttpStatus.OK);
		
	}
}
