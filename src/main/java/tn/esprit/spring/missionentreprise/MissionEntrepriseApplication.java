package tn.esprit.spring.missionentreprise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
<<<<<<< HEAD
=======

@EnableJpaAuditing
>>>>>>> 800784042b3a6f6955d33992fcb8e5a432132e7f

@SpringBootApplication
@EnableJpaAuditing
public class MissionEntrepriseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MissionEntrepriseApplication.class, args);
    }

}
