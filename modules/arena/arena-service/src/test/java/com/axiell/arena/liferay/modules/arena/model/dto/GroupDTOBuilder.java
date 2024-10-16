package com.axiell.arena.liferay.modules.arena.model.dto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GroupDTOBuilder {
    public static final String NAME = "Group name";
    private static final Map<String, String> PROPERTIES = Collections.singletonMap("key1", "value1");
    private String name = NAME;
    private Map<String, GroupDTO> subGroups = new HashMap<>();
    private Map<String, String> properties = PROPERTIES;


    public static GroupDTOBuilder create() {
        return new GroupDTOBuilder();
    }

    public GroupDTOBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public GroupDTOBuilder subGroups(final Map<String, GroupDTO> subGroups) {
        this.subGroups = subGroups;
        return this;
    }

    public GroupDTOBuilder properties(final Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

    public GroupDTO build() {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setName(this.name);
        groupDTO.setSubGroups(this.subGroups);
        groupDTO.setProperties(this.properties);
        return groupDTO;
    }
}
