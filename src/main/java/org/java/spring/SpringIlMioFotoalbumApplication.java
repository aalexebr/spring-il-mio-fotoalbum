package org.java.spring;

import org.java.spring.auth.conf.AuthConfiguration;
import org.java.spring.auth.pojo.db.Role;
import org.java.spring.auth.pojo.db.User;
import org.java.spring.auth.pojo.serv.RoleService;
import org.java.spring.auth.pojo.serv.UserService;
import org.java.spring.pojo.db.Category;
import org.java.spring.pojo.db.Photo;
import org.java.spring.pojo.serv.CategoryService;
import org.java.spring.pojo.serv.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringIlMioFotoalbumApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringIlMioFotoalbumApplication.class, args);
	}
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired 
	private CategoryService catService;
	
	@Override
	public void run(String... args) throws Exception{
		
		Role admin = new Role("ADMIN");
		roleService.save(admin);
		
		Category cat1 = new Category("nature");
		Category cat2 = new Category("portrait");
		catService.save(cat1);
		catService.save(cat2);
		
		Photo p1 = new Photo("t1","url","desc",true,cat1,cat2);
		Photo p2 = new Photo("t2","url","descr",false,cat1);
		photoService.save(p1);
		photoService.save(p2);
		
		String pwsd = AuthConfiguration.passwordEncoder().encode("pswd");
		
		User u = new User("admin",pwsd,admin);
		userService.save(u);
	}

}
