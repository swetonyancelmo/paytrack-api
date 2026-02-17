package com.swetonyancelmo.paytrack.controller;

import com.swetonyancelmo.paytrack.controller.docs.UserControllerDocs;
import com.swetonyancelmo.paytrack.dtos.request.CreateUserDto;
import com.swetonyancelmo.paytrack.dtos.response.UserDto;
import com.swetonyancelmo.paytrack.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/v1")
@Tag(name = "User", description = "Endpoints for Managing User")
public class UserController implements UserControllerDocs {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Override
    public ResponseEntity<UserDto> create(@RequestBody @Valid CreateUserDto dto) {
        UserDto userSaved = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
    }

    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Override
    public ResponseEntity<UserDto> getById(@PathVariable(name = "id") Long id) {
        UserDto user = service.findById(id);
        return ResponseEntity.ok(user);
    }
}
