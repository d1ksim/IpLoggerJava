package com.example.iplogger.repository;

import com.example.iplogger.entity.LoggersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LoggersRepo extends JpaRepository<LoggersEntity, Long> {
    LoggersEntity getLoggerByUuid(UUID uuid);
}
