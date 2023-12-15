package org.java.spring.pojo.repo;

import java.util.List;


import org.java.spring.pojo.db.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer>{
	
	List <Photo> findByTitleContaining(String x);

}
