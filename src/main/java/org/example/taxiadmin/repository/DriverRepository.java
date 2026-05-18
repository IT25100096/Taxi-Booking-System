package org.example.taxiadmin.repository;

import org.example.taxiadmin.model.FleetDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<FleetDriver, Integer> {
}
