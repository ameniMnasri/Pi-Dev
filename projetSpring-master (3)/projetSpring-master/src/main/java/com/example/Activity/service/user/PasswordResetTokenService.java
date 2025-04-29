package com.example.Activity.service.user;


import com.example.Activity.Repository.user.PasswordResetTokenRepository;
import com.example.Activity.Repository.user.UserRepository;
import com.example.Activity.entity.user.PasswordResetToken;
import com.example.Activity.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository; // Assuming you have a User entity

    private final PasswordEncoder passwordEncoder;


    public PasswordResetTokenService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public void sendResetEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new RuntimeException("Email not found");
        }

        // Generate reset token
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(15); // 15 min expiry

        // Save token
        PasswordResetToken resetToken = new PasswordResetToken(token, email, expiryDate);
        tokenRepository.save(resetToken);

        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("testing@gune0984.odns.fr");
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("Click the link below to reset your password:\n" +
                "http://localhost:4200/reset-password?token=" + token);
        mailSender.send(message);
    }

    public boolean validateResetToken(String token) {
        Optional<PasswordResetToken> resetToken = tokenRepository.findByToken(token);
        return resetToken.isPresent() && resetToken.get().getExpiryDate().isAfter(LocalDateTime.now());
    }

    public void resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> resetToken = tokenRepository.findByToken(token);
        if (!resetToken.isPresent() || resetToken.get().getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Invalid or expired token");
        }

        // Find user and update password
        Optional<User> user = userRepository.findByEmail(resetToken.get().getEmail());
        if (!user.isPresent()) {
            throw new RuntimeException("User not found");
        }

        user.get().setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user.get());

        // Delete token after use
        tokenRepository.delete(resetToken.get());
    }
}
