package com.example.iplogger.service;

import com.example.iplogger.entity.LoggersEntity;
import com.example.iplogger.repository.LoggersRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class LoggersService {
    private final LoggersRepo loggersRepo;

    @Autowired
    public LoggersService(LoggersRepo loggersRepo) {
        this.loggersRepo = loggersRepo;
    }

    public LoggersEntity createLogger(LoggersEntity entity) {
        return loggersRepo.save(entity);
    }

    public List<LoggersEntity> getAllLoggers() {
        return loggersRepo.findAll();
    }

    public String getLoggerRedirectUrl(UUID loggerUuid) {
        LoggersEntity loggersEntity = getLoggerByUuid(loggerUuid);
        return loggersEntity.getRedirectUrl();
    }

    public LoggersEntity getLoggerByUuid(UUID loggerUuid) {
        LoggersEntity loggersEntity = loggersRepo.getLoggerByUuid(loggerUuid);
        if (loggersEntity == null) {
            throw new EntityNotFoundException("Logger " + loggerUuid + " not exists");
        }
        return loggersEntity;
    }
}
