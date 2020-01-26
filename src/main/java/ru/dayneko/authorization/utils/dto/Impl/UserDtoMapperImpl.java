package ru.dayneko.authorization.utils.dto.Impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.dayneko.authorization.model.dto.UserDtoDetailed;
import ru.dayneko.authorization.model.dto.UserDto;
import ru.dayneko.authorization.model.dto.UserDtoWithRoleNames;
import ru.dayneko.authorization.model.dto.UserIdAndFioDto;
import ru.dayneko.authorization.model.entity.User;
import ru.dayneko.authorization.utils.dto.UserDtoMapper;

@Service
@AllArgsConstructor
final public class UserDtoMapperImpl implements UserDtoMapper {
    private final ModelMapper modelMapper;

    @Override
    public UserDto convertUserToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserIdAndFioDto convertToIdAndFioDto(User user) {
        return modelMapper.map(user, UserIdAndFioDto.class);
    }

    @Override
    public UserDtoDetailed detailedUserDto(User user) {
        return modelMapper.map(user, UserDtoDetailed.class);
    }

    @Override
    public User convertUserDtoWithRoleNamesToUser(UserDtoWithRoleNames userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
