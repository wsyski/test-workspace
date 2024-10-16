package com.axiell.arena.liferay.modules.arena.model.dto;

public class AgencyMemberDTOBuilder {
    public static final long ID = 4000L;
    public static final String NAME = "Agency Member Name";
    public static final String DESCRIPTION = "Agency Member Description";
    private long id = ID;
    private String name = NAME;
    private String description = DESCRIPTION;
    private GroupDTO mainGroup = GroupDTOBuilder.create().build();


    public static AgencyMemberDTOBuilder create() {
        return new AgencyMemberDTOBuilder();
    }

    public AgencyMemberDTOBuilder id(final long id) {
        this.id = id;
        return this;
    }

    public AgencyMemberDTOBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public AgencyMemberDTOBuilder description(final String description) {
        this.description = description;
        return this;
    }

    public AgencyMemberDTOBuilder mainGroup(final GroupDTO mainGroup) {
        this.mainGroup = mainGroup;
        return this;
    }

    public AgencyMemberDTO build() {
        AgencyMemberDTO AgencyMemberDTO = new AgencyMemberDTO();
        AgencyMemberDTO.setId(this.id);
        AgencyMemberDTO.setName(this.name);
        AgencyMemberDTO.setDescription(this.description);
        AgencyMemberDTO.setMainGroup(this.mainGroup);
        return AgencyMemberDTO;
    }
}
