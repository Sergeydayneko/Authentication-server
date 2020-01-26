package ru.dayneko.authorization.utils.dto;

import ru.dayneko.authorization.model.dto.UserDtoDetailed;
import ru.dayneko.authorization.model.dto.UserDto;
import ru.dayneko.authorization.model.dto.UserDtoWithRoleNames;
import ru.dayneko.authorization.model.dto.UserIdAndFioDto;
import ru.dayneko.authorization.model.entity.User;

/**
 * Мэппуер DTO для пользователей
 *
 * @author dayneko_si
 * @since 13.05.2019
 */
public interface UserDtoMapper {
    UserDto convertUserToUserDto(User user);
    UserIdAndFioDto convertToIdAndFioDto(User user);
    UserDtoDetailed detailedUserDto(User user);
    User convertUserDtoWithRoleNamesToUser(UserDtoWithRoleNames userDto);
}
