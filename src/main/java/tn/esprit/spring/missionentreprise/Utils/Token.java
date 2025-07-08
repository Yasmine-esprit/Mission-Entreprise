package tn.esprit.spring.missionentreprise.Utils;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.spring.missionentreprise.Entities.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String token;
    LocalDateTime creationDate;
    LocalDateTime expirationDate;
    LocalDateTime validatedDate;
    @ManyToOne
    @JoinColumn(name = "userId" , nullable = false)
    User user;
}
