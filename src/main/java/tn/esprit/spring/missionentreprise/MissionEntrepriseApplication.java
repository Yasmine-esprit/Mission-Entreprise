package tn.esprit.spring.missionentreprise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD

@SpringBootApplication
=======
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
>>>>>>> ceadf4d (test)
public class MissionEntrepriseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MissionEntrepriseApplication.class, args);
    }

}
