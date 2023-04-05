package com.example.ipgeofinder.service.impl;

import com.example.ipgeofinder.domain.IpInfo;
import com.example.ipgeofinder.exception.IpGeoFinderException;
import com.example.ipgeofinder.model.EventType;
import com.example.ipgeofinder.model.IpData;
import com.example.ipgeofinder.repository.IpDataRepository;
import com.example.ipgeofinder.service.AuditService;
import com.example.ipgeofinder.service.MapperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class IpInfoServiceTest {

    private IpDataRepository repository;

    private MapperService<IpData, IpInfo> mappingService;

    private AuditService auditService;

    private IpInfoService service;

    @BeforeEach
    public void SetUp() {
        repository = mock(IpDataRepository.class);
        mappingService = mock(MapperService.class);
        auditService = mock(AuditService.class);
        service = new IpInfoService(repository, mappingService, auditService);
    }

    @Test
    public void getIpInfoSuccess() {
        try {
            IpData dataFound = new IpData();
            dataFound.setId(UUID.randomUUID());

            IpInfo ipInfo = IpInfo.builder()
                    .countryCode("CO").build();

            when(repository.findIpInRange(anyLong())).thenReturn(dataFound);
            when(mappingService.map(any())).thenReturn(ipInfo);

            IpInfo result = service.getInfo("192.168.0.1");

            assertEquals("CO", result.getCountryCode());

            verify(repository, times(1)).findIpInRange(anyLong());
            verify(auditService, times(1)).audit("Search IpData for 192.168.0.1 Ip Address", EventType.READ);
            verify(mappingService, times(1)).map(any());
        } catch (IpGeoFinderException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getIpInfoBadIp() {
        try {
            service.getInfo("#");

            verify(repository, times(0)).findIpInRange(anyLong());
            verify(auditService, times(0)).audit("Search IpData for 192.168.0.1 Ip Address", EventType.READ);
            verify(mappingService, times(0)).map(any());
        } catch (IpGeoFinderException e) {
            assertEquals("La dirección IP ingresada no es válida", e.getMessage());
        }
    }

    @Test
    public void getIpInfoWithoutResult() {
        try {
            when(repository.findIpInRange(anyLong())).thenReturn(null);
            when(mappingService.map(any())).thenReturn(null);

            IpInfo result = service.getInfo("192.168.0.1");

            assertNull(result);

            verify(repository, times(1)).findIpInRange(anyLong());
            verify(auditService, times(1)).audit("Search IpData for 192.168.0.1 Ip Address", EventType.READ);
            verify(mappingService, times(1)).map(null);
        } catch (IpGeoFinderException e) {
            fail(e.getMessage());
        }
    }
}
