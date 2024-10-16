package com.axiell.arena.liferay.modules.arena.exception;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArenaRuntimeException extends RuntimeException implements Serializable {

    private final Map<String, Object> attributes = new HashMap<>();

    public ArenaRuntimeException(final String message) {
        super(message);
    }

    public ArenaRuntimeException(final String message, final Throwable ex) {
        super(message, ex);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }


    @Override
    public String toString() {
        String sb = "message: " + getMessage() +
                " attributes: " + Arrays.toString(getAttributes().entrySet().toArray());
        return sb;
    }
}
