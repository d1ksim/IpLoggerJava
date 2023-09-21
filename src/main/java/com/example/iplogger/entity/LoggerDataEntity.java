package com.example.iplogger.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "logger_data")
public class LoggerDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "loggers_logger_data",
            joinColumns = {@JoinColumn(name = "loggers_data_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "logger_id", referencedColumnName = "id")})
    private LoggersEntity loggerUuid;

    @Column(name = "user_ip", nullable = false)
    private String userIp;

    @Column(name = "created")
    @CreatedDate
    @CreationTimestamp
    private Date created;

    public LoggerDataEntity(LoggersEntity loggerUuid, String userIp) {
        this.loggerUuid = loggerUuid;
        this.userIp = userIp;
    }
}
