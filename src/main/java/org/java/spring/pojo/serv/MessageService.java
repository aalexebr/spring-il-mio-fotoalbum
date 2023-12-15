package org.java.spring.pojo.serv;

import java.util.List;

import org.java.spring.auth.pojo.db.User;
import org.java.spring.pojo.db.Message;
import org.java.spring.pojo.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
	
	@Autowired
	private MessageRepository messRepo;
	
	public List<Message> findByUser(User user){
		return messRepo.findByUser(user);
	}
	
	public void save(Message message) {
		messRepo.save(message);
	}
	
	public void delete(Message message) {
		messRepo.delete(message);
	}
	
	public List<Message> findBySender(String sender){
		return messRepo.findBySenderName(sender);
	}

}
