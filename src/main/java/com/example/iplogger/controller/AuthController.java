package com.example.iplogger.controller;

import com.example.iplogger.dto.LoginResponseDto;
import com.example.iplogger.entity.UserEntity;
import com.example.iplogger.security.TokenService;
import com.example.iplogger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class AuthController {
    private final UserService userService;
    private final ApplicationContext context;
    private final TokenService tokenService;

    // FIXME: Добавить try / cath, исправить дерьмо в коде.

    @Autowired
    public AuthController(UserService userService, ApplicationContext context, TokenService tokenService) {
        this.userService = userService;
        this.context = context;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(@RequestBody UserEntity data) {
        if (userService.findByLogin(data.getLogin()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        UserEntity newUser = new UserEntity(data.getLogin(), encryptedPassword);
        userService.createUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody UserEntity data) {
        // TODO: Тут мы ловим ошибку, видать бин пустой AuthenticationManager, см далее SpringSecurityConfig!
        AuthenticationManager authenticationManager = context.getBean(AuthenticationManager.class);

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = this.tokenService.generateToken((UserEntity) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
