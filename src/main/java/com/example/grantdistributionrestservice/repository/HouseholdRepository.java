package com.example.grantdistributionrestservice.repository;

import com.example.grantdistributionrestservice.model.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, Long> {
}
