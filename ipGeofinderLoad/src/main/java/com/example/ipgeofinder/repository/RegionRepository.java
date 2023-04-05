package com.example.ipgeofinder.repository;

import com.example.ipgeofinder.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegionRepository extends JpaRepository<Region, UUID> {
    Optional<Region> findByNameAndCountryId(String name, UUID countryId);
}
