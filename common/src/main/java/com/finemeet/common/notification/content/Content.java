package com.finemeet.common.notification.content;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = EmailContent.class, name = "EMAIL"),
    @JsonSubTypes.Type(value = PushContent.class, name = "PUSH")
})
public abstract class Content {
    // Common properties for all content types can be defined here
}
