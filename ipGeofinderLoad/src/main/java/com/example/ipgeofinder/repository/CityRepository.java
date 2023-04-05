package com.example.ipgeofinder.repository;

import com.example.ipgeofinder.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID> {
    Optional<City> findByNameAndRegionId(String name, UUID regionId);
}
