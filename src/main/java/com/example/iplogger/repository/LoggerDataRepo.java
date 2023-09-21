package com.example.iplogger.repository;

import com.example.iplogger.entity.LoggerDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggerDataRepo extends JpaRepository<LoggerDataEntity, Long> {
}
