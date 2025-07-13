package com.finemeet.authservice.api;

import com.finemeet.authservice.config.AuthUserConstants;
import com.finemeet.authservice.convertor.RegistrationDtoConverter;
import com.finemeet.authservice.dto.UserRegistrationRequest;
import com.finemeet.authservice.dto.UserRegistrationResponse;
import com.finemeet.authservice.entity.AuthUser;
import com.finemeet.authservice.jwt.JwtTokenProvider;
import com.finemeet.authservice.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthUserRepository userCrudRepository;
    private final RegistrationDtoConverter registrationDtoConverter;
    private final PasswordEncoder passwordEncoder;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public UserRegistrationResponse register(final UserRegistrationRequest userRegistrationRequest) {
        String encryptedPassword = passwordEncoder.encode(userRegistrationRequest.getPassword());

        /*
         * request has user-service data and entity has just email and password
         * so need ot handle this case to keep this other data somewhere and then pass to user-service
         */
        UserRegistrationRequest originalRequest = userRegistrationRequest;

        AuthUser newUser = registrationDtoConverter.toEntity(userRegistrationRequest);
        newUser.setPassword(encryptedPassword);
        newUser.setAccountNonLocked(AuthUserConstants.REGISTERED_ACCOUNT_NON_LOCKED);
        newUser.setEmailVerified(AuthUserConstants.REGISTERED_EMAIL_VERIFIED);
        newUser.setLastLogin(AuthUserConstants.REGISTERED_LAST_LOGIN);
        newUser.setEnabled(AuthUserConstants.REGISTERED_ENABLED);

        AuthUser savedUser = userCrudRepository.save(newUser);

        return generateJwtTokens(savedUser);
    }

    private UserRegistrationResponse generateJwtTokens(AuthUser user) {
        final String jwtRefreshToken = jwtTokenProvider.generateRefreshToken(user);
        final String jwtToken = jwtTokenProvider.generateToken(user);
        return UserRegistrationResponse.builder()
                .token(jwtToken)
                .refreshToken(jwtRefreshToken)
                .build();
    }

}

