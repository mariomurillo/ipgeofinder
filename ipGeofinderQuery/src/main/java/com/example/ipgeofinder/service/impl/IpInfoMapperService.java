package com.example.ipgeofinder.service.impl;

import com.example.ipgeofinder.domain.IpInfo;
import com.example.ipgeofinder.model.*;
import com.example.ipgeofinder.service.MapperService;
import org.springframework.stereotype.Service;

@Service
public class IpInfoMapperService implements MapperService<IpData, IpInfo> {
    @Override
    public IpInfo map(IpData source) {
        if (source == null) {
            return null;
        }

        Location location = source.getLocation();
        City city = location.getCity();
        Region region = city.getRegion();
        Country country = region.getCountry();

        return IpInfo.builder()
                .countryCode(country.getCode())
                .region(region.getName())
                .city(city.getName())
                .isp(location.getIsp())
                .build();
    }
}
