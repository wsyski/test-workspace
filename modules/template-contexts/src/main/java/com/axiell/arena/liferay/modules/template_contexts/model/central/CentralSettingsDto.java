package com.axiell.arena.liferay.modules.template_contexts.model.central;

import lombok.Data;

@Data
public class CentralSettingsDto {
    private String agencyName;
    private long agencyId;
    private long agencyMemberId;
    private long portalSiteId;
    private String defaultSourceId;
}
