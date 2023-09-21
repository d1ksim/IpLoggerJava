package com.example.iplogger.controller;

import com.example.iplogger.entity.LoggerDataEntity;
import com.example.iplogger.entity.LoggersEntity;
import com.example.iplogger.service.LoggerDataService;
import com.example.iplogger.service.LoggersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

@RestController
@RequestMapping
public class UserLoggerListener {
    private final LoggersService loggersService;
    private final LoggerDataService loggerDataService;

    @Autowired
    public UserLoggerListener(LoggersService loggersService, LoggerDataService loggerDataService) {
        this.loggersService = loggersService;
        this.loggerDataService = loggerDataService;
    }

    @GetMapping("{loggerUuid}")
    public RedirectView loggerRedirection(@PathVariable String loggerUuid, HttpServletRequest request) {
        String requestUserIp = request.getHeader("X-FORWARDED-FOR");
        LoggersEntity loggersEntity = loggersService.getLoggerByUuid(UUID.fromString(loggerUuid));

        if (requestUserIp != null)
            loggerDataService.createLoggerData(new LoggerDataEntity(loggersEntity, requestUserIp));

        // FIXME: Статус 403, найти решение.
        String redirectUrl = loggersService.getLoggerRedirectUrl(UUID.fromString(loggerUuid));
        return new RedirectView(redirectUrl);
    }
}
