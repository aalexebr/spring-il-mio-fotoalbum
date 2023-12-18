package org.java.spring.pojo.repo;

import java.util.List;

import org.java.spring.auth.pojo.db.User;
import org.java.spring.pojo.db.Category;
import org.java.spring.pojo.db.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer>{
	
	List <Photo> findByTitleContaining(String x);
	Page <Photo> findByTitleContaining(String x, Pageable pageable);
	List <Photo> findByUserUsername(String username);
	List <Photo> findByUser(User user);
	List <Photo> findByVisibleTrue();
	Page <Photo> findByVisibleTrue(Pageable pageable);
	Page <Photo> findByVisibleTrueAndTitleContaining(String title, Pageable pageable);
	List <Photo> findByCategoriesIn(List<Category> categories);
	List <Photo> findByUserAndCategoriesIn(User user, List<Category> categories);
	Page <Photo> findByVisibleTrueAndCategoriesIn(Pageable pageable,List<Category> categories);
	Page <Photo>  findByVisibleTrueAndTitleContainingAndCategoriesIn(Pageable pageable,String name,List<Category> categories);
}
