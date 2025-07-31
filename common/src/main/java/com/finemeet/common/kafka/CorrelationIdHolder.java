package com.finemeet.common.kafka;

import java.util.UUID;
import org.slf4j.MDC;

public class CorrelationIdHolder {
    public static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    public static String getOrGenerate() {
        String existing = MDC.get(CORRELATION_ID_HEADER);
        if (existing != null) return existing;
        String generated = UUID.randomUUID().toString();
        MDC.put(CORRELATION_ID_HEADER, generated);
        return generated;
    }
}
