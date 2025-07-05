package tn.esprit.spring.missionentreprise.Utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;


    public Token createPasswordResetToken(User user) {
        Token token = Token.builder()
                .token(UUID.randomUUID().toString())
                .creationDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusMinutes(30))
                .user(user)
                .build();

        return tokenRepository.save(token);
    }

    public boolean isValid(String tokenValue) {
        return tokenRepository.findByToken(tokenValue)
                .filter(token -> token.getExpirationDate().isAfter(LocalDateTime.now()))
                .isPresent();
    }

    public User getUserByToken(String tokenValue) {
        return tokenRepository.findByToken(tokenValue)
                .map(Token::getUser)
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));
    }

    public void invalidateToken(String tokenValue) {
        tokenRepository.deleteByToken(tokenValue);
    }
}
