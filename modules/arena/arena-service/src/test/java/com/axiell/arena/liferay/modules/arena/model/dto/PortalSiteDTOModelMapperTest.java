package com.axiell.arena.liferay.modules.arena.model.dto;

import com.axiell.arena.liferay.modules.arena.model.PortalSite;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PortalSiteDTOModelMapperTest {
    private final PortalSiteDTOModelMapper underTest = new PortalSiteDTOModelMapper();

    @Test
    public void toModel() {
        PortalSiteDTO portalSiteDTO = PortalSiteDTOBuilder.create().build();
        PortalSite result = underTest.toModel(portalSiteDTO);
        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(PortalSiteDTOBuilder.ID));
        assertThat(result.getName(), is(PortalSiteDTOBuilder.NAME));
        assertThat(result.getDescription(), is(PortalSiteDTOBuilder.DESCRIPTION));
    }

    @Test
    public void toDto() {
        PortalSiteDTO portalSiteDTO = PortalSiteDTOBuilder.create().build();
        PortalSite group = underTest.toModel(portalSiteDTO);
        PortalSiteDTO result = underTest.toDto(group);
        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(PortalSiteDTOBuilder.ID));
        assertThat(result.getName(), is(PortalSiteDTOBuilder.NAME));
        assertThat(result.getDescription(), is(PortalSiteDTOBuilder.DESCRIPTION));
    }
}
