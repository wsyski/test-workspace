package com.axiell.arena.liferay.modules.arena.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
public class Group {
    private String name;
    @EqualsAndHashCode.Exclude
    private Map<String, Group> subGroups;
    @EqualsAndHashCode.Exclude
    private Map<String, String> properties;

    public String getPropertyAsString(final String key) {
        return properties.get(key);
    }

    public boolean getPropertyAsBoolean(final String key) {
        String value = getPropertyAsString(key);
        return Boolean.parseBoolean(value);
    }

    public Long getPropertyAsLong(final String key) {
        String value = getPropertyAsString(key);
        return value == null ? null : Long.parseLong(value);
    }
}
