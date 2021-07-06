package com.example.grantdistributionrestservice.service;

import com.example.grantdistributionrestservice.model.entity.Household;
import com.example.grantdistributionrestservice.model.exceptions.HouseholdNotFoundException;

import java.util.List;

public interface IHouseholdService {

    Household create(Household household);

    Household retrieveHousehold(Long householdId) throws HouseholdNotFoundException;

    List<Household> retrieveAllHouseholds();

    Household deleteHousehold(Long householdId) throws HouseholdNotFoundException;
}
