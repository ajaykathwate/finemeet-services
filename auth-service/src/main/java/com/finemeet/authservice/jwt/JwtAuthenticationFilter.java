package com.finemeet.authservice.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finemeet.authservice.api.SecurityPrincipalProvider;
import com.finemeet.authservice.config.SecurityConstants;
import com.finemeet.authservice.exception.AbsentBearerHeaderException;
import com.finemeet.authservice.exception.JwtTokenBlacklistedException;
import com.finemeet.authservice.exception.JwtTokenHasNoUserEmailException;
import com.finemeet.authservice.exception.dto.ApiErrorResponse;
import com.finemeet.authservice.exception.dto.ApiErrorResponseCreator;
import com.finemeet.authservice.exception.dto.ErrorDebugMessageCreator;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.PathContainer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String MDC_USER_ID_KEY2VALUE = "user.id.key2value";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final PathPatternParser pathPatternParser = new PathPatternParser();
    private static final List<PathPattern> SECURED_PATTERNS = Stream.of(
            SecurityConstants.AUTH_REFRESH_URL
        ).map(pathPatternParser::parse)
         .toList();

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final SecurityPrincipalProvider securityPrincipalProvider;
    private final ApiErrorResponseCreator apiErrorResponseCreator;
    private final ErrorDebugMessageCreator errorDebugMessageCreator;

    @Override
    protected void doFilterInternal(@NonNull final HttpServletRequest httpRequest,
                                    @NonNull final HttpServletResponse httpResponse,
                                    @org.springframework.lang.NonNull final FilterChain filterChain) throws IOException, ServletException {

        try{
            if (shouldNotFilter(httpRequest)) {
                filterChain.doFilter(httpRequest, httpResponse);
                return;
            }

            var authenticationToken = jwtAuthenticationProvider.get(httpRequest);

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authenticationToken);

            UUID userId = securityPrincipalProvider.getUserId();
            MDC.put(MDC_USER_ID_KEY2VALUE, "userId:" + userId.toString());

            filterChain.doFilter(httpRequest, httpResponse);

        } catch (AbsentBearerHeaderException exception) {
            handleException(httpResponse, "Bearer authentication header is absent", exception, HttpServletResponse.SC_BAD_REQUEST);

        } catch (IllegalArgumentException exception) {
            handleException(httpResponse, "JWT token is null or blank", exception, HttpServletResponse.SC_BAD_REQUEST);

        } catch (MalformedJwtException exception) {
            handleException(httpResponse, "JWT token is malformed or corrupted", exception, HttpServletResponse.SC_BAD_REQUEST);

        } catch (UnsupportedJwtException exception) {
            handleException(httpResponse, "JWT token uses an unsupported format or algorithm", exception, HttpServletResponse.SC_BAD_REQUEST);

        } catch (SignatureException exception) {
            handleException(httpResponse, "JWT token signature is invalid", exception, HttpServletResponse.SC_UNAUTHORIZED);

        } catch (SecurityException exception) {
            handleException(httpResponse, "Security issue while verifying token signature", exception, HttpServletResponse.SC_UNAUTHORIZED);

        } catch (ExpiredJwtException exception) {
            handleException(httpResponse, "JWT token is expired", exception, HttpServletResponse.SC_UNAUTHORIZED);

        } catch (JwtTokenHasNoUserEmailException exception) {
            handleException(httpResponse, "User email not found in JWT token", exception, HttpServletResponse.SC_BAD_REQUEST);

        } catch (UsernameNotFoundException exception) {
            handleException(httpResponse, "User with the provided email does not exist", exception, HttpServletResponse.SC_NOT_FOUND);

        } catch (JwtTokenBlacklistedException exception) {
            handleException(httpResponse, "JWT Token is blacklisted", exception, HttpServletResponse.SC_BAD_REQUEST);

        } finally {
            MDC.remove(MDC_USER_ID_KEY2VALUE);
        }
    }

    private void handleException(HttpServletResponse httpResponse,
                                 String errorMessage,
                                 Exception exception,
                                 int statusCode) throws IOException {

        ApiErrorResponse apiErrorResponse = apiErrorResponseCreator.buildResponse(
                errorMessage,
                false,
                HttpStatus.valueOf(statusCode)
        );

        log.warn(
            "Handle resource already exists exception: failed: message: {}, debugMessage: {}.",
            exception.getMessage(),
            errorDebugMessageCreator.buildErrorDebugMessage(exception)
        );

        httpResponse.setStatus(statusCode);
        httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // Write JSON response body
        objectMapper.writeValue(httpResponse.getWriter(), apiErrorResponse);
    }

    @Override
    protected boolean shouldNotFilter(@NotNull HttpServletRequest request) {
        return SECURED_PATTERNS.stream()
               .noneMatch(p -> p.matches(PathContainer.parsePath(request.getServletPath())));
    }

}
