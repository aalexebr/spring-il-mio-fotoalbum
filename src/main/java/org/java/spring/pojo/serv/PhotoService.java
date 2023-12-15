package org.java.spring.pojo.serv;

import java.util.List;

import org.java.spring.pojo.db.Photo;
import org.java.spring.pojo.repo.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {
	
	@Autowired
	private PhotoRepository photoRepo;
	
	public List<Photo> findAll(){
		return photoRepo.findAll();
	}
	
	public Photo findById(int id) {
		return photoRepo.findById(id).orElse(null);
	}
	
	public void save(Photo photo) {
		photoRepo.save(photo);
	}
	
	public List<Photo> findByTitle(String title){
		return photoRepo.findByTitleContaining(title);
	}
	
	public void delete(Photo photo) {
		photoRepo.delete(photo);
	}
	
	public void deleteById(int id) {
		photoRepo.deleteById(id);
	}

}
