package org.java.spring.controller;

import org.java.spring.auth.pojo.db.User;
import org.java.spring.auth.pojo.serv.UserService;
import org.java.spring.pojo.MessageDTO;
import org.java.spring.pojo.db.Message;
import org.java.spring.pojo.serv.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
public class MessageRestController {
	
	@Autowired
	MessageService messService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<Message> store(@RequestBody MessageDTO messDTO){
		
		Message mess = new Message();
		
		mess.setObject(messDTO.getObject());
		mess.setContent(messDTO.getContent());
		mess.setSenderName(messDTO.getSender());
		
		User user = userService.findByName(messDTO.getPhotoAuthor());
		mess.setUser(user);
		
		messService.save(mess);
		
		return new ResponseEntity<>(mess, HttpStatus.OK);
		
	}
	

}
