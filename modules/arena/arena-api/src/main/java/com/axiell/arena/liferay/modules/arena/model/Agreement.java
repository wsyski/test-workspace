package com.axiell.arena.liferay.modules.arena.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public class Agreement {
    public static final String DEFAULT_MIME_TYPE = "application/pdf";

    @NonNull
    String mimeType;
    @NonNull
    byte[] content;

    public Agreement(@NonNull byte[] content) {
        this(DEFAULT_MIME_TYPE, content);
    }
}
