package org.java.spring.pojo.repo;

import java.util.List;

import org.java.spring.auth.pojo.db.User;
import org.java.spring.pojo.db.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer>{
	
	List<Message> findByUser(User user);
	
	List<Message> findBySenderName(String name);

}
