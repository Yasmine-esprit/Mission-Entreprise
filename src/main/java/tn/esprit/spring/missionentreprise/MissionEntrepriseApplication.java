package tn.esprit.spring.missionentreprise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing

@SpringBootApplication
public class MissionEntrepriseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MissionEntrepriseApplication.class, args);
    }

}
