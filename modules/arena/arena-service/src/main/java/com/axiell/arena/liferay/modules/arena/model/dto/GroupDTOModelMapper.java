package com.axiell.arena.liferay.modules.arena.model.dto;

import com.axiell.arena.liferay.modules.arena.model.Group;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class GroupDTOModelMapper {
    public final static GroupDTOModelMapper INSTANCE = new GroupDTOModelMapper();
    private final ModelMapper dto2modelMapper;
    private final ModelMapper model2dtoMapper;

    public GroupDTOModelMapper() {
        dto2modelMapper = new ModelMapper();
        dto2modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        dto2modelMapper.createTypeMap(GroupDTO.class, Group.class);
        dto2modelMapper.validate();

        model2dtoMapper = new ModelMapper();
        model2dtoMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        model2dtoMapper.createTypeMap(Group.class, GroupDTO.class);
        model2dtoMapper.validate();
    }

    public Group toModel(GroupDTO dto) {
        return dto2modelMapper.map(dto, Group.class);
    }

    public GroupDTO toDto(Group model) {
        return model2dtoMapper.map(model, GroupDTO.class);
    }

}
