package org.java.spring.controller;

import org.java.spring.auth.conf.AuthConfiguration;
import org.java.spring.auth.pojo.db.Role;
import org.java.spring.auth.pojo.db.User;
import org.java.spring.auth.pojo.serv.RoleService;
import org.java.spring.auth.pojo.serv.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class UserController {
	
	@Autowired
	UserService userSevice;
	
	@Autowired
	RoleService roleService;
	
	@GetMapping("/register")
	public String regitser(Model model) {
		 User u = new User();
		 model.addAttribute("user", u);
		
		return("register");
	}
	
	@PostMapping("/register")
	public String storeUser(Model model,
			@Valid @ModelAttribute User user,
			BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			
			model.addAttribute("user", user);
			return "register";
		}
		Role r = roleService.findById(1);
		String pwsd = AuthConfiguration.passwordEncoder().encode(user.getPassword());
		User u = new User();
		u.setUsername(user.getUsername());
		u.setRoles(r);
		u.setPassword(pwsd);
		
		
		try {
			userSevice.save(u);
		} catch(Exception e) {
			
			bindingResult.addError(new FieldError("user", "username", u.getUsername(), false, null, null, "Username must be unique"));
			model.addAttribute("user", user);
			return "register";
		}
		return("redirect:/login");
	}

}
