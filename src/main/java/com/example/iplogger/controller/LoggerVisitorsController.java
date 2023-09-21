package com.example.iplogger.controller;
import com.example.iplogger.entity.LoggerDataEntity;
import com.example.iplogger.entity.LoggersEntity;
import com.example.iplogger.service.LoggersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/logger")
public class LoggerVisitorsController {
    private final LoggersService loggersService;

    @Autowired
    public LoggerVisitorsController(LoggersService loggersService) {
        this.loggersService = loggersService;
    }

    @GetMapping("/visitors")
    public ResponseEntity<String> getLogger(@RequestParam String loggerUuid) {
        LoggersEntity loggersEntity = loggersService.getLoggerByUuid(UUID.fromString(loggerUuid));
        LoggerDataEntity loggerDataEntity = loggersEntity.getLoggersData().get(loggersEntity.getLoggersData().size()-1);
        return new ResponseEntity<>(loggerDataEntity.getUserIp(), HttpStatus.OK);
    }
}
