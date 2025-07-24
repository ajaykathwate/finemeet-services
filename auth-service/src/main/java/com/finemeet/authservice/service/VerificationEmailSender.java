package com.finemeet.authservice.service;

import com.finemeet.authservice.dto.UserRegistrationRequest;
import com.finemeet.authservice.entity.AuthUser;
import com.finemeet.authservice.exception.ResourceAlreadyExistsException;
import com.finemeet.authservice.kafka.dto.Channel;
import com.finemeet.authservice.kafka.dto.NotificationEvent;
import com.finemeet.authservice.kafka.dto.NotificationEventType;
import com.finemeet.authservice.kafka.dto.NotificationRecipient;
import com.finemeet.authservice.kafka.dto.content.EmailContent;
import com.finemeet.authservice.kafka.producer.NotificationProducerService;
import com.finemeet.authservice.repository.AuthUserRepository;
import com.finemeet.authservice.token.TokenManager;
import com.finemeet.authservice.validator.UserValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationEmailSender {

    private final TokenManager tokenManager;
    private final AuthUserRepository userCrudRepository;
    private final UserValidator userValidator;
    private final NotificationProducerService notificationProducerService;
    private final Validator validator;

    public void sendVerificationCode(final UserRegistrationRequest request) {
       String email = request.getEmail();

       // throw user already exists exception if email already exists
       userValidator.throwIfEmailExists(email);

       Optional<AuthUser> existingUser = userCrudRepository.findByEmail(email);

       if (existingUser.isPresent()) {
           log.info("User already exists.");
           throw new ResourceAlreadyExistsException("User", "Email");
       }

       String token = tokenManager.generateToken(request);
       String verificationLink = "http://localhost:8082/api/auth/verify?token=" + token;
       Map<String, Object> templateData= Map.of(
           "name", request.getFirstName(),
           "verificationLink", verificationLink,
           "email", request.getEmail()
        );

        // send verification email using Kafka to topic
        NotificationEvent notificationEvent = buildNotificationEvent(request, templateData);
        validateNotificationEvent(notificationEvent);
        notificationProducerService.sendUserRegisteredEvent(notificationEvent);

       log.info("Generated Token for {} is: {}", request, token);
    }

    public NotificationEvent buildNotificationEvent(UserRegistrationRequest userRegistrationRequest, Map<String, Object> templateData) {

        // Build EmailContent using builder
        EmailContent emailContent = EmailContent.builder()
                .templateId("email_verification")
                .subject("Welcome to FineMeet! Confirm Your Email to Get Started")
                .templateData(templateData)
                .build();

        // Build NotificationRecipient using builder
        NotificationRecipient emailRecipient = NotificationRecipient.builder()
                .channel(Channel.EMAIL)
                .recipient(userRegistrationRequest.getEmail())
                .content(emailContent)
                .build();
        
        // Build and return NotificationEvent using builder
        return NotificationEvent.builder()
                .recipientId(userRegistrationRequest.getEmail())
                .eventType(NotificationEventType.USER_REGISTERED)
                .channels(List.of(Channel.EMAIL))
                .scheduledAt(null)
                .metadata(Map.of())
                .recipients(List.of(emailRecipient))
                .build();
    }

    public void validateNotificationEvent(NotificationEvent notificationEvent) {
        // Validator for NotificationEvent
        Set<ConstraintViolation<NotificationEvent>> violations = validator.validate(notificationEvent);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("NotificationEvent is invalid", violations);
        }
    }
}
