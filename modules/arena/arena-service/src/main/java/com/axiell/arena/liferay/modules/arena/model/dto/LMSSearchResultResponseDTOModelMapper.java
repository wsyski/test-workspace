package com.axiell.arena.liferay.modules.arena.model.dto;

import com.axiell.arena.liferay.modules.arena.model.LMSSearchResponse;
import com.axiell.arena.liferay.modules.arena.model.LMSSearchResultResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.stream.Collectors;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class LMSSearchResultResponseDTOModelMapper {
    public final static LMSSearchResultResponseDTOModelMapper INSTANCE = new LMSSearchResultResponseDTOModelMapper();
    private final ModelMapper dao2dtoMapper;
    private final ModelMapper dto2daoMapper;

    private final Converter<List<LMSSearchResponseDTO>, List<LMSSearchResponse>> lmsSearchResponseDto2lmsSearchResponse = ctx ->
            ctx.getSource() != null ? ctx.getSource().stream().map(LMSSearchResponseDTOModelMapper.INSTANCE::toModel).collect(Collectors.toList()) : null;

    private final Converter<List<LMSSearchResponse>, List<LMSSearchResponseDTO>> lmsSearchResponse2lmsSearchResponseDto = ctx ->
            ctx.getSource() != null ? ctx.getSource().stream().map(LMSSearchResponseDTOModelMapper.INSTANCE::toDto).collect(Collectors.toList()) : null;

    public LMSSearchResultResponseDTOModelMapper() {
        dao2dtoMapper = new ModelMapper();
        dao2dtoMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        dao2dtoMapper.createTypeMap(LMSSearchResultResponseDTO.class, LMSSearchResultResponse.class)
                .addMappings(mapper -> mapper.using(lmsSearchResponseDto2lmsSearchResponse).map(LMSSearchResultResponseDTO::getLmsList, LMSSearchResultResponse::setLmsList));
        dao2dtoMapper.validate();

        dto2daoMapper = new ModelMapper();
        dto2daoMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        dto2daoMapper.createTypeMap(LMSSearchResultResponse.class, LMSSearchResultResponseDTO.class)
                .addMappings(mapper -> mapper.using(lmsSearchResponse2lmsSearchResponseDto).map(LMSSearchResultResponse::getLmsList, LMSSearchResultResponseDTO::setLmsList));
        dto2daoMapper.validate();
    }

    public LMSSearchResultResponse toModel(LMSSearchResultResponseDTO dto) {
        return dao2dtoMapper.map(dto, LMSSearchResultResponse.class);
    }

    public LMSSearchResultResponseDTO toDto(LMSSearchResultResponse model) {
        return dto2daoMapper.map(model, LMSSearchResultResponseDTO.class);
    }

}
