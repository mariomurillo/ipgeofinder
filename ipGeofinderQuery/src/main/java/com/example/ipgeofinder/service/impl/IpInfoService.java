package com.example.ipgeofinder.service.impl;

import com.example.ipgeofinder.domain.IpInfo;
import com.example.ipgeofinder.exception.IpGeoFinderException;
import com.example.ipgeofinder.model.EventType;
import com.example.ipgeofinder.model.IpData;
import com.example.ipgeofinder.repository.IpDataRepository;
import com.example.ipgeofinder.service.AuditService;
import com.example.ipgeofinder.service.InfoService;
import com.example.ipgeofinder.service.MapperService;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class IpInfoService implements InfoService {

    private final static String IP_REGEX = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$|^([0-9a-fA-F]{1,4}:){7}([0-9a-fA-F]{1,4})$";

    private final IpDataRepository ipDataRepository;

    private final MapperService<IpData, IpInfo> mappingService;

    private final AuditService auditService;

    public IpInfoService(IpDataRepository ipDataRepository, MapperService mappingService, AuditService auditService) {
        this.ipDataRepository = ipDataRepository;
        this.mappingService = mappingService;
        this.auditService = auditService;
    }

    @Override
    public IpInfo getInfo(String ipAddress) throws IpGeoFinderException {

        try {
            if (!isIpAddressValid(ipAddress)) {
                throw new IpGeoFinderException("La dirección IP ingresada no es válida");
            }
            Long ipDecimal = ipToDecimal(ipAddress);

            IpData IpDataFound = ipDataRepository.findIpInRange(ipDecimal);
            auditService.audit(String.format("Search IpData for %s Ip Address", ipAddress), EventType.READ);
            IpInfo ipInfo = mappingService.map(IpDataFound);
            return ipInfo;
        } catch (IpGeoFinderException e) {
            throw e;
        }
    }

    private boolean isIpAddressValid(String ipAddress) throws IpGeoFinderException {
        boolean valido = false;
        try {
            // Validar la dirección IP utilizando la clase InetAddress
            InetAddress ip = InetAddress.getByName(ipAddress);

            // Validar que la dirección IP sea válida (IPv4 o IPv6)
            if (ip.getHostAddress().matches(IP_REGEX)) {
                valido = true;
            }
        } catch (UnknownHostException e) {
            // La dirección IP no es válida
            throw new IpGeoFinderException("La dirección IP ingresada no es válida", e);
        }
        return valido;
    }


    private Long ipToDecimal(String ipAddress) {
        String[] segments = ipAddress.split("\\.");
        long decimal = 0;

        for (int i = 0; i < segments.length; i++) {
            int segmentValue = Integer.parseInt(segments[i]);
            decimal += segmentValue * Math.pow(256, (3 - i));
        }

        return decimal;
    }

}
