package tn.esprit.spring.missionentreprise.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.missionentreprise.Repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServImpl implements UserDetailsService {

    private final UserRepository userRepository;



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return userRepository.findByEmailUser(userEmail).orElseThrow(() -> new UsernameNotFoundException(userEmail));
    }
}
