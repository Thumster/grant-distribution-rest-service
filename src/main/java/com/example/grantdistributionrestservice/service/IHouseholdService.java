package com.example.grantdistributionrestservice.service;

import com.example.grantdistributionrestservice.model.entity.Household;
import com.example.grantdistributionrestservice.model.exceptions.HouseholdNotFoundException;
import com.example.grantdistributionrestservice.model.exceptions.SearchHouseholdException;
import com.example.grantdistributionrestservice.model.vo.SearchHouseholdsReq;

import java.util.List;

public interface IHouseholdService {

    Household create(Household household);

    Household retrieveHousehold(Long householdId) throws HouseholdNotFoundException;

    List<Household> retrieveAllHouseholds();

    Household deleteHousehold(Long householdId) throws HouseholdNotFoundException;

    List<Household> searchHouseholds(SearchHouseholdsReq req) throws SearchHouseholdException;
}
