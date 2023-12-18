package org.java.spring.controller;

import java.util.List;

import org.java.spring.auth.pojo.db.User;
import org.java.spring.auth.pojo.serv.UserService;
import org.java.spring.pojo.db.Message;
import org.java.spring.pojo.serv.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
	
	@Autowired
	MessageService messService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/messages")
	public String messageIndex(Model model,
				@RequestParam(required = false) String sender,
				@AuthenticationPrincipal UserDetails userDetails) {
		String username = userDetails.getUsername();
		User user = userService.findByName(username);
		
		List<Message> messages = sender != null ? messService.findByUserAndSender(user, sender)
				: messService.findByUser(user);
		
		model.addAttribute("messages", messages);
		if(sender != null) model.addAttribute("sender", sender);
		
		return("messages");
	}
	
	@GetMapping("/message/{id}")
	public String showMessage(Model model, @PathVariable int id,
			@AuthenticationPrincipal UserDetails userDetails) {
		String username = userDetails.getUsername();
		User user = userService.findByName(username);
		Message mess = messService.findByUserAndId(user, id);
		model.addAttribute("message", mess);
		
		mess.setRead_mess(true);
		messService.save(mess);
		return("single-message");
	}
	
	@PostMapping("/message/delete/{id}")
	public String deleteMess(@PathVariable int id,
			@AuthenticationPrincipal UserDetails userDetails) {
		String username = userDetails.getUsername();
		User user = userService.findByName(username);
		
		Message mess =messService.findByUserAndId(user, id);
		messService.delete(mess);
		
		return "redirect:/messages";
	}
	

}
