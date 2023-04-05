package com.example.ipgeofinder.repository;

import com.example.ipgeofinder.model.IpData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IpDataRepository extends JpaRepository<IpData, UUID> {
}
