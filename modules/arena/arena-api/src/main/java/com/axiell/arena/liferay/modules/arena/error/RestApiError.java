package com.axiell.arena.liferay.modules.arena.error;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestApiError implements Serializable {
    public static final String ARGUMENT_THROWABLE_CLASS_NAME = "throwableClassName";
    @NonNull
    private ErrorCause cause;
    @NonNull
    private String message;
    private String localizedMessage;
    private Map<String, String> arguments = new HashMap<>();
    private String stackTrace;

    private RestApiError(final Builder builder) {
        this.cause = builder.cause;
        this.message = builder.message;
        this.localizedMessage = builder.localizedMessage;
        this.arguments = builder.arguments;
        Throwable throwable = builder.throwable;
        if (throwable != null && builder.isDebug) {
            StringWriter sw = new StringWriter();
            throwable.printStackTrace(new PrintWriter(sw));
            stackTrace = sw.toString();
        }
    }

    @RequiredArgsConstructor
    public static final class Builder implements Serializable {
        @NonNull
        ErrorCause cause;
        @NonNull
        String message;
        String localizedMessage;
        Throwable throwable;
        Map<String, String> arguments = new HashMap<>();
        boolean isDebug = false;

        public Builder localizedMessage(final String localizedMessage) {
            this.localizedMessage = localizedMessage;
            return this;
        }

        public Builder arguments(final Map<String, String> arguments) {
            this.arguments.putAll(arguments);
            return this;
        }

        public Builder argument(final String key, final String value) {
            arguments.put(key, value);
            return this;
        }

        public Builder throwable(final Throwable exception) {
            this.throwable = exception;
            return this;
        }

        public Builder isDebug(final boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }


        public RestApiError build() {
            return new RestApiError(this);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
