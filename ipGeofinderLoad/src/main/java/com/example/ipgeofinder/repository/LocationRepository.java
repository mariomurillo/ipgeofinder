package com.example.ipgeofinder.repository;

import com.example.ipgeofinder.model.City;
import com.example.ipgeofinder.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
    @Query("SELECT l FROM Location l WHERE l.city.id = ?1 AND l.latitude = ?2 AND l.longitude = ?3")
    Optional<Location> findByCityIdAndLatitudeAndLongitude(UUID cityId, double latitude, double longitude);
}
