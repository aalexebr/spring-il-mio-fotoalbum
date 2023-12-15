package org.java.spring.controller;

import java.util.List;

import org.java.spring.pojo.db.Photo;
import org.java.spring.pojo.serv.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PhotoRestController {
	
	@Autowired
	PhotoService photoService;
	
	@GetMapping("all")
	public ResponseEntity<List<Photo>> index(@RequestParam(required=false) String name){
		
		List<Photo> photos = photoService.findVisiblePhotos();
		
		if(photos.size() == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(photos, HttpStatus.OK);
		
	}
	
	 @GetMapping("/index")
	    public ResponseEntity<Page<Photo>> getVisiblePhotos(@RequestParam(defaultValue = "0") int page) {
		 
		 	Pageable pageable = PageRequest.of(page, 3); 
	        Page<Photo> visiblePhotos = photoService.getVisiblePhotos(pageable);
	        return new ResponseEntity<>(visiblePhotos, HttpStatus.OK);
	    }

}
