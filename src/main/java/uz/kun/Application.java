package uz.kun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uz.kun.entity.ProfileEntity;
import uz.kun.enums.ProfileRole;
import uz.kun.enums.ProfileStatus;
import uz.kun.service.ProfileService;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

/*
	@Autowired
	private ProfileService service;

	@Bean
	CommandLineRunner ok() {
		return args -> {
			ProfileEntity moderator = new ProfileEntity();
			moderator.setName("moderator");
			moderator.setSurname("kunUZ");
			moderator.setPassword("1234");
			moderator.setStatus(ProfileStatus.ACTIVE);
			moderator.setRole(ProfileRole.MODERATOR);
			moderator.setEmail("moderator@mail.ru");
			service.save(moderator);

			ProfileEntity admin = new ProfileEntity();
			admin.setName("admin");
			admin.setSurname("kunUZ");
			admin.setPassword("1234");
			admin.setEmail("admin@mail.ru");
			admin.setStatus(ProfileStatus.ACTIVE);
			admin.setRole(ProfileRole.ADMIN);
			service.save(admin);

			ProfileEntity publisher = new ProfileEntity();
			publisher.setName("publisher");
			publisher.setSurname("kunUZ");
			publisher.setPassword("1234");
			publisher.setEmail("publisher@mail.ru");
			publisher.setStatus(ProfileStatus.ACTIVE);
			publisher.setRole(ProfileRole.PUBLISHER);
			service.save(publisher);

			ProfileEntity user = new ProfileEntity();
			user.setName("user");
			user.setSurname("kunUZ");
			user.setPassword("1234");
			user.setEmail("user@mail.ru");
			user.setStatus(ProfileStatus.ACTIVE);
			user.setRole(ProfileRole.USER);
			service.save(user);

		};
	}*/
}
