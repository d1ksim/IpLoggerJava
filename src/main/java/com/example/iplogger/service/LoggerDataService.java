package com.example.iplogger.service;

import com.example.iplogger.entity.LoggerDataEntity;
import com.example.iplogger.entity.LoggersEntity;
import com.example.iplogger.repository.LoggerDataRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LoggerDataService {
    private final LoggerDataRepo loggerDataRepo;

    @Autowired
    public LoggerDataService(LoggerDataRepo loggerDataRepo) {
        this.loggerDataRepo = loggerDataRepo;
    }

    public void createLoggerData(LoggerDataEntity entity) {
        loggerDataRepo.save(entity);
    }

    public List<LoggerDataEntity> getAllLoggerDataByUuid(LoggersEntity loggerData) {
        List<LoggerDataEntity> loggersList = loggerData.getLoggersData();
        if (loggersList == null) {
            throw new EntityNotFoundException("Logger data by " + loggerData.getUuid() + " not exist");
        }
        return loggersList;
    }
}
