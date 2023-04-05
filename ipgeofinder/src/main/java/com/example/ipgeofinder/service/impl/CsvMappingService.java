package com.example.ipgeofinder.service.impl;

import com.example.ipgeofinder.model.*;
import com.example.ipgeofinder.repository.CityRepository;
import com.example.ipgeofinder.repository.CountryRepository;
import com.example.ipgeofinder.repository.LocationRepository;
import com.example.ipgeofinder.repository.RegionRepository;
import com.example.ipgeofinder.service.AuditService;
import com.example.ipgeofinder.service.MappingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvMappingService implements MappingService {

    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;
    private final LocationRepository locationRepository;

    private final AuditService auditService;

    public CsvMappingService(CountryRepository countryRepository, RegionRepository regionRepository, CityRepository cityRepository, LocationRepository locationRepository, AuditService auditService) {
        this.countryRepository = countryRepository;
        this.regionRepository = regionRepository;
        this.cityRepository = cityRepository;
        this.locationRepository = locationRepository;
        this.auditService = auditService;
    }

    //TODO: It's necessary refactor this method do move the logic of find and save to different services
    @Override
    public List<IpData> mapBlockToEntity(String[][] block, int count) {
        List<IpData> ipDataList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String[] row = block[i];
            IpData ipData = new IpData();
            ipData.setIpFrom(Long.valueOf(row[0]));
            ipData.setIpTo(Long.valueOf(row[1]));

            Country country = countryRepository.findByCode(row[2]).orElse(null);
            if (country == null) {
                country = new Country();
                country.setCode(row[2]);
                country.setName(row[3]);
                countryRepository.save(country);
            }

            Region region = regionRepository.findByNameAndCountryId(row[4], country.getId()).orElse(null);
            if (region == null) {
                region = new Region();
                region.setName(row[4]);
                region.setCountry(country);
                regionRepository.save(region);
            }

            City city = cityRepository.findByNameAndRegionId(row[5], region.getId()).orElse(null);
            if (city == null) {
                city = new City();
                city.setName(row[5]);
                city.setRegion(region);
                cityRepository.save(city);
            }

            Location location = locationRepository.findByCityIdAndLatitudeAndLongitude(city.getId(), Double.parseDouble(row[6]), Double.parseDouble(row[7])).orElse(null);
            if (location == null) {
                location = new Location();
                location.setLatitude(BigDecimal.valueOf(Double.parseDouble(row[6])));
                location.setLongitude(BigDecimal.valueOf(Double.parseDouble(row[7])));
                location.setIsp(row[8]);
                location.setCity(city);
                locationRepository.save(location);
            }
            ipData.setLocation(location);
            ipDataList.add(ipData);
        }
        return ipDataList;
    }
}
