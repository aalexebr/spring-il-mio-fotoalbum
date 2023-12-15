package org.java.spring.pojo.db;

import org.java.spring.auth.pojo.db.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private int id;
	
	private String object;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;
	
	private String senderName;
	
	@ManyToOne
	private User user;
	
	public Message() {}
	
	public Message( String content, String sender, User user) {
		setContent(content);
		setUser(user);
		setSenderName(sender);
	}
	
	public Message(String object, String content, String sender, User user) {
		setObject(object);
		setContent(content);
		setUser(user);
		setSenderName(sender);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "object:"+getObject()+"[contents:"+getContent()+"]";
	}

}
