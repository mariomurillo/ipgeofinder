package com.example.ipgeofinder.service.impl;

import com.example.ipgeofinder.model.AuditLog;
import com.example.ipgeofinder.model.EventType;
import com.example.ipgeofinder.repository.AuditLogRepository;
import com.example.ipgeofinder.service.AuditService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DefaultAuditService implements AuditService {

    private final AuditLogRepository auditLogRepository;

    private final String appName;

    public DefaultAuditService(AuditLogRepository auditLogRepository,
                               @Value("${spring.application.name}") String appName) {
        this.auditLogRepository = auditLogRepository;
        this.appName = appName;
    }

    @Override
    public void audit(String data, EventType eventType) {
        auditLogRepository.save(AuditLog.builder()
                .source(appName)
                .eventType(eventType)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build());
    }
}
