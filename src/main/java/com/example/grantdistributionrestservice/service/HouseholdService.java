package com.example.grantdistributionrestservice.service;

import com.example.grantdistributionrestservice.model.entity.Household;
import com.example.grantdistributionrestservice.model.exceptions.HouseholdNotFoundException;
import com.example.grantdistributionrestservice.repository.HouseholdRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HouseholdService implements IHouseholdService {
    @Autowired
    HouseholdRepository repository;

    @Override
    public Household create(Household household) {
        log.info("Creating household...");

        repository.save(household);
        repository.flush();

        log.info("Done creating household! householdId: " + household.getHouseholdId());
        return household;
    }

    @Override
    public Household retrieveHousehold(Long householdId) throws HouseholdNotFoundException {
        Household household = repository.findById(householdId)
                .orElseThrow(() -> new HouseholdNotFoundException("Household with Id: " + householdId + " not found"));

        log.info("Successfully retrieved household: " + householdId);
        household.getFamilyMembers().forEach(fm -> {
            log.info("\tFamily Member: " + fm.getFamilyMemberId());
        });

        return household;
    }

    @Override
    public List<Household> retrieveAllHouseholds() {
        List<Household> households = repository.findAll();
        households.forEach(h -> {
            log.info("Successfully retrieved household: " + h.getHouseholdId());
            h.getFamilyMembers().forEach(fm -> {
                log.info("\tFamily Member: " + fm.getFamilyMemberId());
            });
        });
        return households;
    }

    @Override
    public Household deleteHousehold(Long householdId) throws HouseholdNotFoundException {
        Household household = retrieveHousehold(householdId);
        repository.delete(household);
        repository.flush();
        return household;
    }
}
