package com.example.iplogger.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "loggers")
public class LoggersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false)
    private UUID uuid;

    @Column(name = "redirect_url", nullable = false)
    private String redirectUrl;

    @Column(name = "created")
    @CreatedDate
    @CreationTimestamp
    private Date created;

    @OneToMany(mappedBy = "loggerUuid")
    private List<LoggerDataEntity> loggersData;

    public LoggersEntity(UUID uuid, String redirectUrl) {
        this.uuid = uuid;
        this.redirectUrl = redirectUrl;
    }
}
