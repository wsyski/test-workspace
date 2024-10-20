package com.axiell.arena.liferay.modules.arena.model.dto;

import com.axiell.arena.liferay.modules.arena.model.AgencyMemberSummary;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AgencyMemberSummaryDTOModelMapper {

    public final static AgencyMemberSummaryDTOModelMapper INSTANCE = new AgencyMemberSummaryDTOModelMapper();
    private final ModelMapper dao2dtoMapper;
    private final ModelMapper dto2daoMapper;

    public AgencyMemberSummaryDTOModelMapper() {
        dao2dtoMapper = new ModelMapper();
        dao2dtoMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        dao2dtoMapper.createTypeMap(AgencyMemberSummaryDTO.class, AgencyMemberSummary.class);
        dao2dtoMapper.validate();

        dto2daoMapper = new ModelMapper();
        dto2daoMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        dto2daoMapper.createTypeMap(AgencyMemberSummary.class, AgencyMemberSummaryDTO.class);
        dto2daoMapper.validate();
    }

    public AgencyMemberSummary toModel(AgencyMemberSummaryDTO dto) {
        return dao2dtoMapper.map(dto, AgencyMemberSummary.class);
    }

    public AgencyMemberSummaryDTO toDto(AgencyMemberSummary model) {
        return dto2daoMapper.map(model, AgencyMemberSummaryDTO.class);
    }
}
