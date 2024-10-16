package com.axiell.arena.liferay.modules.arena.model;

import lombok.Data;

@Data
public class AgencyMemberSummary {
    private Long id;
    private String name;
    private String description;
    private String mobileWelcomePage;
    private boolean mobileAppEnabled;
}
