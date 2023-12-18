package org.java.spring.pojo.repo;

import java.util.List;

import org.java.spring.pojo.db.Category;
import org.java.spring.pojo.db.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	Category findByName(String name);
}
