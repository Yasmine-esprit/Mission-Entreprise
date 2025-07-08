package tn.esprit.spring.missionentreprise.Utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender; //erreur à ignorer
    //private final SpringTemplateEngine templateEngine;



    @Async
    public void sendEmail(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true); // `true` means HTML
        helper.setFrom("mariemoueslati165@gmail.com");

        try {
            mailSender.send(message); // Send the email
        } catch (MailException e) {
            // Handle exception if sending fails
            e.printStackTrace();
        }
    }

    @Async
    public void sendTwoFactorSetupEmail(String toEmail, String userName, String qrCodeUrl) throws MessagingException {

        // Encode l'URL pour la passer en paramètre URL
        String encodedQrCodeUrl = URLEncoder.encode(qrCodeUrl, StandardCharsets.UTF_8);

        // URL propre vers ta page Angular (exemple)
        String frontendUrl = "http://localhost:4200/qrCode?data=" + encodedQrCodeUrl;

        // Contenu HTML personnalisé avec un joli style simple
        String htmlContent = "<html>" +
                "<body style=\"font-family: Arial, sans-serif; color: #333;\">" +
                "<h2>Bonjour " + userName + ",</h2>" +
                "<p>Merci de vous être inscrit ! Pour finaliser la configuration de votre authentification à deux facteurs (2FA), " +
                "veuillez cliquer sur le lien ci-dessous :</p>" +
                "<p><a href=\"" + frontendUrl + "\" " +
                "style=\"display: inline-block; padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; " +
                "border-radius: 5px;\">Configurer mon 2FA</a></p>" +
                "<p>Si vous n'avez pas demandé cette action, vous pouvez ignorer cet email.</p>" +
                "<br>" +
                "<p>Cordialement,<br>L'équipe Support</p>" +
                "</body>" +
                "</html>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject("Configurer votre authentification à deux facteurs (2FA)");
        helper.setText(htmlContent, true); // true pour indiquer que c'est du HTML

        mailSender.send(message);
    }
}
