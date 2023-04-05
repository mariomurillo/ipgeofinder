package com.example.ipgeofinder.repository;

import com.example.ipgeofinder.model.IpData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IpDataRepository extends JpaRepository<IpData, UUID> {
    @Query("SELECT i FROM IpData i WHERE :ip BETWEEN i.ipFrom AND i.ipTo")
    IpData findIpInRange(@Param("ip") Long ip);

}
