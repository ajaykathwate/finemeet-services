package com.finemeet.authservice.service.notification;

import com.finemeet.authservice.config.VerificationProperties;
import com.finemeet.authservice.dto.UserRegistrationRequest;
import com.finemeet.authservice.kafka.producer.NotificationProducerService;
import com.finemeet.authservice.service.RegistrationPreconditionChecker;
import com.finemeet.authservice.token.TokenManager;
import com.finemeet.common.notification.NotificationEvent;
import com.finemeet.common.service.NotificationEventValidationService;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationEmailNotifier {

    private final TokenManager tokenManager;
    private final NotificationProducerService notificationProducerService;
    private final RegistrationPreconditionChecker preconditionChecker;
    private final VerificationProperties verificationProperties;
    private final NotificationEventValidationService eventValidationService;

    public void sendVerificationCode(final UserRegistrationRequest request) {
        String email = request.getEmail();

        // Unified precondition check
        preconditionChecker.checkEmailNotTaken(email);

        String token = tokenManager.generateToken(request);
        String verificationLink = verificationProperties.buildVerificationUrl(token);

        Map<String, Object> templateData = Map.of(
                "name", request.getFirstName(),
                "verificationLink", verificationLink,
                "email", request.getEmail()
        );

        // send verification email using Kafka to topic
        NotificationEvent notificationEvent =
            NotificationEventFactory.userRegisteredEvent(email, templateData);
        eventValidationService.validateOrThrow(notificationEvent);

        notificationProducerService.sendUserRegisteredEvent(notificationEvent);
        log.info("Generated Token for {} is: {}", request, token);
    }
}
