package com.example.ipgeofinder.service;

import com.example.ipgeofinder.model.EventType;

public interface AuditService {

    void audit(String data, EventType eventType);
}
