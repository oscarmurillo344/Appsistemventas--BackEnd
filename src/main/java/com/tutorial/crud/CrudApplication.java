package com.tutorial.crud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudApplication {
	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}
	/** @Component
	public class CreateRoles implements CommandLineRunner {

		@Autowired
		RolService rolService;

		@Override
		public void run(String... args) throws Exception {
			 Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
			 Rol rolUser = new Rol(RolNombre.ROLE_USER);
			 rolService.save(rolAdmin);
			 rolService.save(rolUser);
		}
	}**/
}
