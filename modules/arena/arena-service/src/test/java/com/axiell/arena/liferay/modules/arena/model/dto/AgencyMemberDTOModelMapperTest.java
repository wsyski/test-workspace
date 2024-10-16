package com.axiell.arena.liferay.modules.arena.model.dto;

import com.axiell.arena.liferay.modules.arena.model.AgencyMember;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class AgencyMemberDTOModelMapperTest {
    private final AgencyMemberDTOModelMapper underTest = new AgencyMemberDTOModelMapper();

    @Test
    public void toModel() {
        AgencyMemberDTO AgencyMemberDTO = AgencyMemberDTOBuilder.create().build();
        AgencyMember result = underTest.toModel(AgencyMemberDTO);
        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(AgencyMemberDTOBuilder.ID));
        assertThat(result.getName(), is(AgencyMemberDTOBuilder.NAME));
        assertThat(result.getDescription(), is(AgencyMemberDTOBuilder.DESCRIPTION));
    }

    @Test
    public void toDto() {
        AgencyMemberDTO AgencyMemberDTO = AgencyMemberDTOBuilder.create().build();
        AgencyMember group = underTest.toModel(AgencyMemberDTO);
        AgencyMemberDTO result = underTest.toDto(group);
        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(AgencyMemberDTOBuilder.ID));
        assertThat(result.getName(), is(AgencyMemberDTOBuilder.NAME));
        assertThat(result.getDescription(), is(AgencyMemberDTOBuilder.DESCRIPTION));
    }
}
