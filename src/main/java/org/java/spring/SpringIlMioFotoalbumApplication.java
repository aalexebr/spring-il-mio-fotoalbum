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
		Role superadmin = new Role("SUPERADMIN");
		roleService.save(superadmin);
		
		String pwsd = AuthConfiguration.passwordEncoder().encode("pswd");
		
		User u = new User("admin1",pwsd,admin);
		User u2 = new User("admin2",pwsd,admin);
		User u3 = new User("admin3",pwsd,superadmin);
		userService.save(u);
		userService.save(u2);
		userService.save(u3);
		
		Message m = new Message("content","sender",u);
		Message m2 = new Message("object1","content","sender",u);
		messService.save(m);
		messService.save(m2);
		
		Category cat1 = new Category("nature");
		Category cat2 = new Category("portrait");
		catService.save(cat1);
		catService.save(cat2);
		
		int n = 1;
		
		Photo p1 = new Photo("t1","https://picsum.photos/300?random="+n,"desc",true,u,cat1,cat2);
		Photo p2 = new Photo("t2","https://picsum.photos/300?random="+(n+=1),"desc",true,u,cat2);
		Photo p3 = new Photo("t3","https://picsum.photos/300?random="+(n+=1),"desc",true,u,cat1);
		Photo p4 = new Photo("t4","https://picsum.photos/300?random="+(n+=1),"desc",true,u,cat1);
		Photo p5 = new Photo("t5","https://picsum.photos/300?random="+(n+=1),"desc",true,u,cat1);
		Photo p6 = new Photo("t6","https://picsum.photos/300?random="+(n+=1),"desc",true,u2,cat1);
		Photo p7 = new Photo("t7","https://picsum.photos/300?random="+(n+=1),"desc",true,u2,cat2);
		Photo p8 = new Photo("t8","https://picsum.photos/300?random="+(n+=1),"desc",true,u2,cat2);
		Photo p9 = new Photo("t9","https://picsum.photos/300?random="+(n+=1),"descr",false,u2,cat2);
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
