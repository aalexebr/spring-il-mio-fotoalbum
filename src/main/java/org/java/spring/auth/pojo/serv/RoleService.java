package org.java.spring.auth.pojo.serv;

import java.util.List;

import org.java.spring.auth.pojo.db.Role;
import org.java.spring.auth.pojo.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
	
	@Autowired
	RoleRepository roleRepo;
	
	public List<Role> findAll() {
		
		return roleRepo.findAll();
	}
	public Role findById(int id) {
		
		return roleRepo.findById(id).get();
	}
	public void save(Role role) {
		
		roleRepo.save(role);
	}

}
