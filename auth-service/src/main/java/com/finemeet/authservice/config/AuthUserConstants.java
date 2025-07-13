package com.finemeet.authservice.config;

import java.time.LocalDateTime;

public class AuthUserConstants {
     /*
     * User Registered Values
     */
    public static final boolean REGISTERED_ACCOUNT_NON_LOCKED = true;
    public static final boolean REGISTERED_EMAIL_VERIFIED = true;
    public static final LocalDateTime REGISTERED_LAST_LOGIN = LocalDateTime.now();
    public static final boolean REGISTERED_ENABLED = true;
}
