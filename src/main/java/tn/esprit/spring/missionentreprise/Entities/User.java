package tn.esprit.spring.missionentreprise.Entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idUser")
public class User implements UserDetails, Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idUser ;

    String nomUser ;
    String prenomUser ;
    @Column(unique=true)
    String emailUser ;

    String passwordUser ;

    boolean enabledUser ;

    boolean accountLockedUser ;

    @CreatedDate
    @Column(nullable = false , updatable = false )
    LocalDateTime createdDate ;
    @LastModifiedDate
    @Column(insertable = false)
    LocalDateTime lastmodifiedDate ;

    @Lob
    byte[] photoProfil;


    @ManyToMany(fetch = FetchType.EAGER)// Roles are stored in the database in a many-to-many relationship

    Collection<Role> roles;

     String secret;
    @OneToMany(mappedBy = "user")
    List <Post> posts;

    @OneToMany(mappedBy = "user")
    List <Interaction> interactions;

    @ManyToMany
    @JoinTable(
            name = "user_groups",
            joinColumns = @JoinColumn(name = "users_id_user"),
            inverseJoinColumns = @JoinColumn(name = "groups_id_grp_msg")
    )
    private Set<GroupeMsg> groups = new HashSet<>();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream().map(r->new SimpleGrantedAuthority(r.getRoleType().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return passwordUser;
    }

    @Override
    public String getUsername() {
        return emailUser;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLockedUser;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabledUser;
    }

    @Override
    public String getName() {
        return emailUser;
    }

    public String getFullName(){
       return this.nomUser + ' ' + this.prenomUser;
    }
}
