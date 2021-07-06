package com.example.grantdistributionrestservice.repository;

import com.example.grantdistributionrestservice.model.entity.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
}
