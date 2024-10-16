package com.axiell.arena.liferay.modules.arena.model.dto;

import com.axiell.arena.liferay.modules.arena.model.LMSSearchResponse;
import com.axiell.arena.liferay.modules.arena.model.UberkeyResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class LMSSearchResponseDTOModelMapper {

    public final static LMSSearchResponseDTOModelMapper INSTANCE = new LMSSearchResponseDTOModelMapper();
    private final ModelMapper dao2dtoMapper;
    private final ModelMapper dto2daoMapper;

    private final Converter<UberkeyResponseDTO, UberkeyResponse> uberkeyResponseDto2uberkeyResponse = ctx ->
            ctx.getSource() != null ? UberkeyResponseDTOModelMapper.INSTANCE.toModel(ctx.getSource()) : null;

    private final Converter<UberkeyResponse, UberkeyResponseDTO> uberkeyResponse2uberkeyResponseDto = ctx ->
            ctx.getSource() != null ? UberkeyResponseDTOModelMapper.INSTANCE.toDto(ctx.getSource()) : null;

    public LMSSearchResponseDTOModelMapper() {
        dao2dtoMapper = new ModelMapper();
        dao2dtoMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        dao2dtoMapper.createTypeMap(LMSSearchResponseDTO.class, LMSSearchResponse.class)
                .addMappings(mapper -> mapper.using(uberkeyResponseDto2uberkeyResponse).map(LMSSearchResponseDTO::getUberkey, LMSSearchResponse::setUberkey));
        dao2dtoMapper.validate();

        dto2daoMapper = new ModelMapper();
        dto2daoMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        dto2daoMapper.createTypeMap(LMSSearchResponse.class, LMSSearchResponseDTO.class)
                .addMappings(mapper -> mapper.using(uberkeyResponseDto2uberkeyResponse).map(LMSSearchResponse::getUberkey, LMSSearchResponseDTO::setUberkey));
        dto2daoMapper.validate();
    }

    public LMSSearchResponse toModel(LMSSearchResponseDTO dto) {
        return dao2dtoMapper.map(dto, LMSSearchResponse.class);
    }

    public LMSSearchResponseDTO toDto(LMSSearchResponse model) {
        return dto2daoMapper.map(model, LMSSearchResponseDTO.class);
    }

}
