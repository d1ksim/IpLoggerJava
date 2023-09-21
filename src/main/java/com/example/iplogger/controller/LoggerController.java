package com.example.iplogger.controller;

import com.example.iplogger.dto.LoggerCreateResponseDto;
import com.example.iplogger.dto.LoggerCreateRequestDto;
import com.example.iplogger.entity.LoggerDataEntity;
import com.example.iplogger.entity.LoggersEntity;
import com.example.iplogger.service.LoggersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1")
public class LoggerController {
    private final LoggersService loggersService;

    @Value("${application.url-address}")
    private String urlAddress;

    @Autowired
    public LoggerController(LoggersService loggersService) {
        this.loggersService = loggersService;
    }

    @PostMapping("/create-logger")
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

    @GetMapping("/get-logger")
    public ResponseEntity<String> getLogger(@RequestParam String loggerUuid) {
        LoggersEntity loggersEntity = loggersService.getLoggerByUuid(UUID.fromString(loggerUuid));
        LoggerDataEntity loggerDataEntity = loggersEntity.getLoggersData().get(loggersEntity.getLoggersData().size()-1);
        return new ResponseEntity<>(loggerDataEntity.getUserIp(), HttpStatus.OK);
    }
}
