package com.finemeet.authservice.exception.token;

import java.time.Duration;
import java.time.OffsetDateTime;
import lombok.Getter;

@Getter
public class TimeTokenException extends RuntimeException {

    private final String email;

    public TimeTokenException(String email, OffsetDateTime expireTime) {
        super(buildMessageError(email, expireTime));
        this.email = email;
    }

    private static String buildMessageError(String email, OffsetDateTime expireTime) {
        StringBuilder stringBuilder = new StringBuilder();
        Duration remainingTime = Duration.between(OffsetDateTime.now(), expireTime);
        long minutes = remainingTime.toMinutesPart();
        long seconds = remainingTime.toSecondsPart();

        stringBuilder.append("A token has already been sent to '")
             .append(email)
             .append("'. Please try again in ");

        if (minutes > 0) {
            stringBuilder.append(minutes).append(" minute");
            if (minutes > 1) stringBuilder.append("s");
            if (seconds > 0) stringBuilder.append(" ");
        }

        if (seconds > 0 || minutes == 0) {
            stringBuilder.append(seconds).append(" second");
            if (seconds != 1) stringBuilder.append("s.");
        }

        return stringBuilder.toString();
    }
}


