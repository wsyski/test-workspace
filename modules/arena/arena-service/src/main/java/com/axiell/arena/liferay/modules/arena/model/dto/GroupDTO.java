package com.axiell.arena.liferay.modules.arena.model.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GroupDTO {
    private String name;
    @EqualsAndHashCode.Exclude
    private Map<String, GroupDTO> subGroups;
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
