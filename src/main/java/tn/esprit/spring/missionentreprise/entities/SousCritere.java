package tn.esprit.spring.missionentreprise.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.tool.schema.spi.SchemaTruncator;
import org.springframework.boot.autoconfigure.web.WebProperties;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)


public class SousCritere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSousCritere;
    @Column(nullable = false)
    private String descriptionSousCritere;



}
