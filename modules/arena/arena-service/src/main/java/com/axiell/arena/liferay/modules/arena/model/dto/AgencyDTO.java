package com.axiell.arena.liferay.modules.arena.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class AgencyDTO {
    private long id;
    @EqualsAndHashCode.Exclude
    private String name;
    @EqualsAndHashCode.Exclude
    private String description;
}
