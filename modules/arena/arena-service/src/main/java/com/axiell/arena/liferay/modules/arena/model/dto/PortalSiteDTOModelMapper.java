package com.axiell.arena.liferay.modules.arena.model.dto;

import com.axiell.arena.liferay.modules.arena.model.AgencyMemberSummary;
import com.axiell.arena.liferay.modules.arena.model.Group;
import com.axiell.arena.liferay.modules.arena.model.PortalSite;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.stream.Collectors;

public class PortalSiteDTOModelMapper {

    public final static PortalSiteDTOModelMapper INSTANCE = new PortalSiteDTOModelMapper();
    private final ModelMapper dao2dtoMapper;
    private final ModelMapper dto2daoMapper;

    private final Converter<GroupDTO, Group> groupDto2group = ctx ->
            ctx.getSource() != null ? GroupDTOModelMapper.INSTANCE.toModel(ctx.getSource()) : null;

    private final Converter<Group, GroupDTO> group2GroupDto = ctx ->
            ctx.getSource() != null ? GroupDTOModelMapper.INSTANCE.toDto(ctx.getSource()) : null;


    private final Converter<List<AgencyMemberSummaryDTO>, List<AgencyMemberSummary>> agencyMemberSummaryDto2agencyMemberSummary = ctx ->
            ctx.getSource() != null ? ctx.getSource().stream().map(AgencyMemberSummaryDTOModelMapper.INSTANCE::toModel).collect(Collectors.toList()) : null;

    private final Converter<List<AgencyMemberSummary>, List<AgencyMemberSummaryDTO>> agencyMemberSummary2agencyMemberSummaryDto = ctx ->
            ctx.getSource() != null ? ctx.getSource().stream().map(AgencyMemberSummaryDTOModelMapper.INSTANCE::toDto).collect(Collectors.toList()) : null;

    public PortalSiteDTOModelMapper() {
        dao2dtoMapper = new ModelMapper();
        dao2dtoMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        dao2dtoMapper.createTypeMap(PortalSiteDTO.class, PortalSite.class)
                .addMappings(mapper -> mapper.using(groupDto2group).map(PortalSiteDTO::getMainGroup, PortalSite::setMainGroup))
                .addMappings(mapper -> mapper.using(groupDto2group).map(PortalSiteDTO::getPortalSiteGroup, PortalSite::setPortalSiteGroup))
                .addMappings(mapper -> mapper.using(agencyMemberSummaryDto2agencyMemberSummary).map(PortalSiteDTO::getAgencyMemberSummaries, PortalSite::setAgencyMemberSummaries));
        dao2dtoMapper.validate();

        dto2daoMapper = new ModelMapper();
        dto2daoMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        dto2daoMapper.createTypeMap(PortalSite.class, PortalSiteDTO.class)
                .addMappings(mapper -> mapper.using(group2GroupDto).map(PortalSite::getMainGroup, PortalSiteDTO::setMainGroup))
                .addMappings(mapper -> mapper.using(group2GroupDto).map(PortalSite::getPortalSiteGroup, PortalSiteDTO::setPortalSiteGroup))
                .addMappings(mapper -> mapper.using(agencyMemberSummary2agencyMemberSummaryDto).map(PortalSite::getAgencyMemberSummaries, PortalSiteDTO::setAgencyMemberSummaries));
        dto2daoMapper.validate();
    }

    public PortalSite toModel(PortalSiteDTO dto) {
        return dao2dtoMapper.map(dto, PortalSite.class);
    }

    public PortalSiteDTO toDto(PortalSite model) {
        return dto2daoMapper.map(model, PortalSiteDTO.class);
    }

}
