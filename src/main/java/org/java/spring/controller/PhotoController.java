package org.java.spring.controller;

import java.util.List;

import org.java.spring.auth.pojo.db.User;
import org.java.spring.auth.pojo.serv.UserService;
import org.java.spring.pojo.db.Category;
import org.java.spring.pojo.db.Photo;
import org.java.spring.pojo.serv.CategoryService;
import org.java.spring.pojo.serv.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
public class PhotoController {
	
	@Autowired
	PhotoService photoService;
	
	@Autowired
	CategoryService catService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public String home(Model model,
			@RequestParam(required = false) String query) {
		
//		List<Photo> photos = query != null ? photoService.findByTitle(query) : photoService.findAll();
//		
//		model.addAttribute("photos",photos);
		model.addAttribute("query", query);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
//        User user = userService.findByName(username);
        
        List<Photo> photos2 = query != null ? photoService.findByTitleAndUserName(username,query) 
        		:photoService.findByUserName(username);
        model.addAttribute("photos",photos2);
         
//		System.out.println(photoService.findVisiblePhotos());
		return("home");
	}
	
	@GetMapping("/photo/{id}")
	public String showPhoto(Model model, @PathVariable int id) {
		
		Photo photo = photoService.findById(id);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        if (photo != null) {
            List<Photo> userPhotos = photoService.findByUserName(username);
            if (userPhotos.contains(photo)) {
                model.addAttribute("photo", photo);
                return("single-photo");
            }
        }

		
        return "redirect:/";
	}
	
	@GetMapping("/create/photo")
	public String create(Model model) {
		
		String title = "create";
		List<Category> categories = catService.findAll();
		Photo photo = new Photo();
		
		model.addAttribute("title", title);
		model.addAttribute("categories", categories);
		model.addAttribute("photo", photo);
		return("photo-form");
	}
	
	@PostMapping("/create/photo")
	public String store(Model model ,@Valid @ModelAttribute Photo photo,
			BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			
			System.out.println(bindingResult);
			model.addAttribute("photo", photo);
			return "photo-form";
		}
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//		User user =userService.findByName(username);
		photo.setUser(getAuthUser());
		photoService.save(photo);
		
		return("redirect:/");
	}
	
	@GetMapping("/update/photo/{id}")
	public String update(Model model, @PathVariable int id ){
		
		String title = "update";
		List<Category> categories = catService.findAll();
		Photo photo = photoService.findById(id);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        if (photo != null) {
            List<Photo> userPhotos = photoService.findByUserName(username);
            if (userPhotos.contains(photo)) {
            	model.addAttribute("title", title);
        		model.addAttribute("categories", categories);
        		model.addAttribute("photo", photo);
        		return("photo-form");
            }
        }
		
        return("redirect:/");
	}
	
	@PostMapping("/update/photo/{id}")
	public String updatePhoto(Model model ,@Valid @ModelAttribute Photo photo,
			BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			
			System.out.println(bindingResult);
			model.addAttribute("photo", photo);
			return "photo-form";
		}
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//		User user =userService.findByName(username);
		photo.setUser(getAuthUser());
		
		photoService.save(photo);
		
		return("redirect:/");
	}
	
	@PostMapping("/delete/photo/{id}")
	public String delete(@PathVariable int id) {
		
		Photo p = photoService.findById(id);
		
		photoService.delete(p);
		
		return "redirect:/";
	}
	
	private  User getAuthUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
		User user =userService.findByName(username);
		return user;
	}
}
