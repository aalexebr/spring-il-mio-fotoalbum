package org.java.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.java.spring.auth.pojo.db.Role;
import org.java.spring.auth.pojo.db.User;
import org.java.spring.auth.pojo.serv.UserService;
import org.java.spring.pojo.db.Category;
import org.java.spring.pojo.db.Photo;
import org.java.spring.pojo.serv.CategoryService;
import org.java.spring.pojo.serv.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
			@RequestParam(required = false) String query,
			@RequestParam(defaultValue = "0") int page) {
		
		model.addAttribute("query", query);
		User u = getAuthUser();
		System.out.println(u.getRoles());
		if(userHasRole(u,"SUPERADMIN")) {
			
			
			return("redirect:/super");
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        
        List<Photo> photos2 = query != null ? photoService.findByTitleAndUserName(username,query) 
        		:photoService.findByUserName(username);
        model.addAttribute("photos",photos2);
         
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
			String title = "create";
			List<Category> categories = catService.findAll();
			System.out.println(bindingResult);
			model.addAttribute("photo", photo);
			model.addAttribute("title", title);
			model.addAttribute("categories", categories);
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
			String title = "update";
			List<Category> categories = catService.findAll();
			
			System.out.println(bindingResult);
			model.addAttribute("title", title);
    		model.addAttribute("categories", categories);
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
	
	@GetMapping("/super")
	public String SuperAdminHome(Model model,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(required = false) String title){
		
		if(title != null) {
			int pageSize = 100;
			Page <Photo> photos = photoService.findByTitlePaginated(title,PageRequest.of(page, pageSize));
			model.addAttribute("photos", photos);
			User u = getAuthUser();
			model.addAttribute("superadmin",u);
			return "super-admin-home";
		}
		int pageSize = 3;
		User u = getAuthUser();
		Page<Photo> photos = photoService.findAllPaginated(PageRequest.of(page, pageSize));
		model.addAttribute("photos", photos);
		model.addAttribute("superadmin",u);
		
		return "super-admin-home";
	}
	
	@PostMapping("/super/visible/{id}")
	public String modVisibilty(Model model,@PathVariable int id) {
		
		Photo p = photoService.findById(id);
		p.setVisible(!p.isVisible());
		photoService.save(p);
		return"redirect:/super";
	}
	
	private  User getAuthUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
		User user =userService.findByName(username);
		return user;
	}
	
	private boolean userHasRole(User user, String roleName) {

		for (Role role : user.getRoles()) {
			if (role.getName().equals(roleName)) {
				return true;
			}
		}

		return false;
	}
}
