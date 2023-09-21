package com.example.iplogger.controller;

import com.example.iplogger.dto.LoggerCreateRequestDto;
import com.example.iplogger.dto.LoggerCreateResponseDto;
import com.example.iplogger.entity.LoggersEntity;
import com.example.iplogger.service.LoggersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/create")
public class LoggerCreateController {
    private final LoggersService loggersService;

    @Value("${application.url-address}")
    private String urlAddress;

    public LoggerCreateController(LoggersService loggersService) {
        this.loggersService = loggersService;
    }

    @PostMapping("/short-link")
    public ResponseEntity<LoggerCreateResponseDto> createLogger(@RequestBody LoggerCreateRequestDto request) {
        LoggersEntity newLogger = loggersService.createLogger(
                new LoggersEntity(UUID.randomUUID(), request.getRedirectUrl())
        );

        LoggerCreateResponseDto response = LoggerCreateResponseDto.builder()
                .loggerId(newLogger.getUuid())
                .loggerLink(urlAddress + newLogger.getUuid())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
