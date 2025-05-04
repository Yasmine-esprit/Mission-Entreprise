package tn.esprit.spring.missionentreprise.Utils;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class EmailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Set your mail properties here
        mailSender.setHost("smtp.example.com");  // Example SMTP host (replace with actual)
        mailSender.setPort(587);  // Port (can be 587 or 465 for most SMTP services)

        // Set authentication details (if needed)
        mailSender.setUsername("your-email@example.com");
        mailSender.setPassword("your-email-password");

        // Set additional properties (e.g., SSL or TLS settings)
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.timeout", "5000");

        return mailSender;
    }
}
