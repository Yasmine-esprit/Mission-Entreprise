package tn.esprit.spring.missionentreprise.Auth;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
public class TwoFactorAuthenticationService {
    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    // Method to generate a secret key for a user
    public String generateSecret() {
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key.getKey();  // This is the secret key that should be saved in the DB
    }

    // Method to generate a QR code URL
    public String generateQRCode(String username, String secret) {
        String format = "otpauth://totp/%s?secret=%s&issuer=MyApp";
        String label = String.format(format, username, secret);
        return generateQRCodeImage(label);
    }

    // Method to generate the actual QR code image (base64 encoded)
    private String generateQRCodeImage(String content) {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 200, 200);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
            byte[] qrImage = byteArrayOutputStream.toByteArray();
            return Base64.getEncoder().encodeToString(qrImage);  // Return Base64 encoded string
        } catch (Exception e) {
            throw new RuntimeException("Error generating QR code", e);
        }
    }

    // Method to validate the code entered by the user
    public boolean validateCode(String secret, int code) {
        return gAuth.authorize(secret, code);
    }

}
