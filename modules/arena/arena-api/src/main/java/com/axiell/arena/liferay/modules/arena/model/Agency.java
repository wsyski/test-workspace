package com.axiell.arena.liferay.modules.arena.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Agency {
    private long id;
    @EqualsAndHashCode.Exclude
    private String name;
    @EqualsAndHashCode.Exclude
    private String description;
}
