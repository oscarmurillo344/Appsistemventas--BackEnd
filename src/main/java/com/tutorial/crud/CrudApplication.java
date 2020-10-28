package com.tutorial.crud;
import com.tutorial.crud.security.entity.Rol;
import com.tutorial.crud.security.enums.RolNombre;
import com.tutorial.crud.security.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudApplication {
	@Autowired
	public static RolService rolService;
	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
		Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
		Rol rolUser = new Rol(RolNombre.ROLE_USER);
		rolService.save(rolAdmin);
		rolService.save(rolUser);
	}
	/** @Component
	public class CreateRoles implements CommandLineRunner {



		@Override
		public void run(String... args) throws Exception {
			 Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
			 Rol rolUser = new Rol(RolNombre.ROLE_USER);
			 rolService.save(rolAdmin);
			 rolService.save(rolUser);
		}
	}**/
}
