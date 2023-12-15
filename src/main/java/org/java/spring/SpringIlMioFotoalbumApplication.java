package org.java.spring;

import org.java.spring.auth.conf.AuthConfiguration;
import org.java.spring.auth.pojo.db.Role;
import org.java.spring.auth.pojo.db.User;
import org.java.spring.auth.pojo.serv.RoleService;
import org.java.spring.auth.pojo.serv.UserService;
import org.java.spring.pojo.db.Category;
import org.java.spring.pojo.db.Message;
import org.java.spring.pojo.db.Photo;
import org.java.spring.pojo.serv.CategoryService;
import org.java.spring.pojo.serv.MessageService;
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
	
	@Autowired
	private MessageService messService;
	
	@Override
	public void run(String... args) throws Exception{
		
		Role admin = new Role("ADMIN");
		roleService.save(admin);
		
		String pwsd = AuthConfiguration.passwordEncoder().encode("pswd");
		
		User u = new User("admin",pwsd,admin);
		userService.save(u);
		
		Message m = new Message("content","sender",u);
		messService.save(m);
		
		Category cat1 = new Category("nature");
		Category cat2 = new Category("portrait");
		catService.save(cat1);
		catService.save(cat2);
		
		Photo p1 = new Photo("t11","url","desc",true,u,cat1,cat2);
		Photo p2 = new Photo("t2","url","desc",true,u,cat1,cat2);
		Photo p3 = new Photo("t3","url","desc",true,u,cat1,cat2);
		Photo p4 = new Photo("t4","url","desc",true,u,cat1,cat2);
		Photo p5 = new Photo("t5","url","desc",true,u,cat1,cat2);
		Photo p6 = new Photo("t6","url","desc",true,u,cat1,cat2);
		Photo p7 = new Photo("t7","url","desc",true,u,cat1,cat2);
		Photo p8 = new Photo("t8","url","desc",true,u,cat1,cat2);
		Photo p9 = new Photo("t9","url","descr",false,u,cat1);
		photoService.save(p1);
		photoService.save(p2);
		photoService.save(p3);
		photoService.save(p4);
		photoService.save(p5);
		photoService.save(p6);
		photoService.save(p7);
		photoService.save(p8);
		photoService.save(p9);
	}

}
