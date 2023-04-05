package com.example.ipgeofinder.controller;

import com.example.ipgeofinder.domain.IpInfo;
import com.example.ipgeofinder.exception.IpGeoFinderException;
import com.example.ipgeofinder.service.InfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/info")
public class IpGeoFinderController {

    private final InfoService<IpInfo> infoService;

    public IpGeoFinderController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/ip/{ipAddress}")
    public ResponseEntity getIpInfo(@PathVariable String ipAddress) {
        IpInfo ipInfo;
        try {
            ipInfo = infoService.getInfo(ipAddress);
            if (ipInfo != null) {
                return ResponseEntity.ok(ipInfo);
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "No se encontró información para la dirección IP: " + ipAddress);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (IpGeoFinderException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
