package ru.dayneko.authorization.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dayneko.authorization.model.dto.UserDtoDetailed;
import ru.dayneko.authorization.model.dto.UserDto;
import ru.dayneko.authorization.exceptions.UserNotFoundInDatabaseException;
import ru.dayneko.authorization.model.dto.UserDtoWithRoleNames;
import ru.dayneko.authorization.services.UserService;
import ru.dayneko.authorization.utils.dto.UserDtoMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с пользователями системы авторизации
 *
 * @author dayneko_si
 * @since 08.05.2019
 */
@RestController
@AllArgsConstructor
@RequestMapping("/v1")
@Api(tags = "Контроллер для работы с пользователями системы авторизации")
final public class UserController {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @ApiOperation("Получение всех пользователей")
    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(userDtoMapper::convertUserToUserDto)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @ApiOperation("Получение пользователя по ID")
    @GetMapping("/user")
    public UserDtoDetailed getUserById(@RequestParam long id) throws UserNotFoundInDatabaseException {
        return userDtoMapper.detailedUserDto(userService.getUserById(id));
    }

    @ApiOperation("Создание нового пользователя")
    @PostMapping("/user")
    public ResponseEntity createNewUser(@RequestBody UserDtoWithRoleNames userDto) {
        userService.saveUser(userDto);
        return ResponseEntity.status(201).build();
    }

    @ApiOperation("Обновление пользователя")
    @PutMapping("/user")
    public ResponseEntity updateUser(@RequestBody UserDtoWithRoleNames userDto) {
        userService.saveUser(userDto);
        return ResponseEntity.ok().build();
    }
}
