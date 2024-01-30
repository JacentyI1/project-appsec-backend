package com.jack.webapp.config;

import com.jack.webapp.domain.entities.Role;
import com.jack.webapp.domain.entities.UserEntity;
import com.jack.webapp.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

public class DatabaseInitlzr {

    @Bean
    public CommandLineRunner initDatabase(UserService userService) {
        return args -> {
            try{

                UserEntity admin1 = new UserEntity();
                admin1.setUsername("J4CK");
                admin1.setEmail("phantom1@mail.com");
                admin1.setPassword("verySecurePassword1"); // You should encrypt this
                admin1.setRole(Role.ADMIN);
                admin1.setActive(true);
                userService.save(admin1);

                UserEntity admin2 = new UserEntity();
                admin2.setUsername("PR73M3K");
                admin2.setEmail("phantom2@mail.com");
                admin2.setPassword("verySecurePassword2"); // You should encrypt this
                admin2.setRole(Role.ADMIN);
                admin2.setActive(true);
                userService.save(admin2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
