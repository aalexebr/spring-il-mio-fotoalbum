package org.java.spring.pojo.serv;

import java.util.ArrayList;
import java.util.List;

import org.java.spring.auth.pojo.db.User;
import org.java.spring.pojo.db.Category;
import org.java.spring.pojo.db.Photo;
import org.java.spring.pojo.repo.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {
	
	@Autowired
	private PhotoRepository photoRepo;
	
	public List<Photo> findAll(){
		return photoRepo.findAll();
	}
	
	public Page<Photo> findAllPaginated(Pageable pageable){
		return photoRepo.findAll(pageable);
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
	
	public Page<Photo> findByTitlePaginated(String title,Pageable pageable){
		return photoRepo.findByTitleContaining(title,pageable);
	}
	
	public void delete(Photo photo) {
		photoRepo.delete(photo);
	}
	
	public void deleteById(int id) {
		photoRepo.deleteById(id);
	}
	
	public List<Photo> findByUserName(String username){
		return photoRepo.findByUserUsername(username);
	}
	
	public List<Photo> findByTitleAndUserName(String username, String title){
		List<Photo> photos = photoRepo.findByUserUsername(username);
		List <Photo> n = new ArrayList<>();
		for (Photo p : photos) {
			if(p.getTitle().contains(title)) {
				n.add(p);
			}
		}
		
		return n;
	}
	
	public List<Photo> findVisiblePhotos(){
		return photoRepo.findByVisibleTrue();
	}
	
//	public Page<Photo> findVisiblePhotosPaginated(Pageable pageable) {
//        return photoRepo.findByVisibleTrue(pageable);
//    }
	
	public Page<Photo> getVisiblePhotos(Pageable pageable) {
        return photoRepo.findByVisibleTrue(pageable);
    }
	
	public Page<Photo> getVisiblePhotos(String title, Pageable pageable) {
        return photoRepo.findByVisibleTrueAndTitleContaining(title,pageable);
    }
	
	public List<Photo> findAllPhotoByCategories(List<Category> categories){
		return photoRepo.findByCategoriesIn(categories);
	}
	
	public List<Photo> findByUserAndCategoriesIn(User user, List<Category> categories){
		return photoRepo.findByUserAndCategoriesIn(user, categories);
	}
	
	public Page<Photo> findByVisibleTrueAndCategoriesIn(Pageable pageable,List<Category> categories){
		return photoRepo.findByVisibleTrueAndCategoriesIn(pageable, categories);
	}
	
	public Page<Photo> findByVisibleTrueAndTitleContainingAndCategoriesIn(Pageable pageable,String name,List<Category> categories){
		return photoRepo.findByVisibleTrueAndTitleContainingAndCategoriesIn(pageable, name, categories);
	}

}
