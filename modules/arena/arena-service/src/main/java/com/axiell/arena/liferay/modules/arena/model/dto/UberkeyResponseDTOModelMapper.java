package com.axiell.arena.liferay.modules.arena.model.dto;

import com.axiell.arena.liferay.modules.arena.model.UberkeyResponse;
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
public class UberkeyResponseDTOModelMapper {

    public final static UberkeyResponseDTOModelMapper INSTANCE = new UberkeyResponseDTOModelMapper();
    private final ModelMapper dao2dtoMapper;
    private final ModelMapper dto2daoMapper;

    public UberkeyResponseDTOModelMapper() {
        dao2dtoMapper = new ModelMapper();
        dao2dtoMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        dao2dtoMapper.createTypeMap(UberkeyResponseDTO.class, UberkeyResponse.class);
        dao2dtoMapper.validate();

        dto2daoMapper = new ModelMapper();
        dto2daoMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        dto2daoMapper.createTypeMap(UberkeyResponse.class, UberkeyResponseDTO.class);
        dto2daoMapper.validate();
    }

    public UberkeyResponse toModel(UberkeyResponseDTO dto) {
        return dao2dtoMapper.map(dto, UberkeyResponse.class);
    }

    public UberkeyResponseDTO toDto(UberkeyResponse model) {
        return dto2daoMapper.map(model, UberkeyResponseDTO.class);
    }

}
