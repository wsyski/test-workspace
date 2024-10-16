package com.axiell.arena.liferay.modules.arena.model.dto;

import com.axiell.arena.liferay.modules.arena.model.Agency;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class AgencyDTOModelMapper {

    public final static AgencyDTOModelMapper INSTANCE = new AgencyDTOModelMapper();
    private final ModelMapper dao2dtoMapper;
    private final ModelMapper dto2daoMapper;

    public AgencyDTOModelMapper() {
        dao2dtoMapper = new ModelMapper();
        dao2dtoMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        dao2dtoMapper.createTypeMap(AgencyDTO.class, Agency.class);
        dao2dtoMapper.validate();

        dto2daoMapper = new ModelMapper();
        dto2daoMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        dto2daoMapper.createTypeMap(Agency.class, AgencyDTO.class);
        dto2daoMapper.validate();
    }

    public Agency toModel(AgencyDTO dto) {
        return dao2dtoMapper.map(dto, Agency.class);
    }

    public AgencyDTO toDto(Agency model) {
        return dto2daoMapper.map(model, AgencyDTO.class);
    }

}
