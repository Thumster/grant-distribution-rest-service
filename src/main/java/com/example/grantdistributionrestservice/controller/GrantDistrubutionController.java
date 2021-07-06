package com.example.grantdistributionrestservice.controller;

import com.example.grantdistributionrestservice.model.entity.FamilyMember;
import com.example.grantdistributionrestservice.model.entity.Household;
import com.example.grantdistributionrestservice.model.exceptions.FamilyMemberNotFoundException;
import com.example.grantdistributionrestservice.model.exceptions.HouseholdNotFoundException;
import com.example.grantdistributionrestservice.model.exceptions.SearchHouseholdException;
import com.example.grantdistributionrestservice.model.vo.CreateFamilyMemberReq;
import com.example.grantdistributionrestservice.model.vo.SearchHouseholdsReq;
import com.example.grantdistributionrestservice.service.IFamilyMemberService;
import com.example.grantdistributionrestservice.service.IHouseholdService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@NoArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GrantDistrubutionController {

    @Autowired
    IHouseholdService householdService;

    @Autowired
    IFamilyMemberService familyMemberService;

    @PostMapping(value = "/create-household",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Household createHousehold(@Valid @RequestBody Household household) {
        return householdService.create(household);
    }

    @GetMapping(value = "/retrieve-household/{householdId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Household retrieveHousehold(@PathVariable Long householdId) throws HouseholdNotFoundException {
        return householdService.retrieveHousehold(householdId);
    }

    @GetMapping(value = "/retrieve-all-households",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Household> retrieveHouseholds() {
        return householdService.retrieveAllHouseholds();
    }

    @DeleteMapping(value = "/delete-household/{householdId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Household deleteHousehold(@PathVariable Long householdId) throws HouseholdNotFoundException {
        return householdService.deleteHousehold(householdId);
    }

    @PostMapping(value = "/create-family-member",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FamilyMember createFamilyMember(@Valid @RequestBody CreateFamilyMemberReq createFamilyMemberVO)
            throws HouseholdNotFoundException, FamilyMemberNotFoundException {
        return familyMemberService.createFamilyMember(createFamilyMemberVO.getFamilyMemberToCreate(),
                createFamilyMemberVO.getHouseholdId(),
                createFamilyMemberVO.getSpouseId());
    }

    @DeleteMapping(value = "/delete-family-member/{familyMemberId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FamilyMember deleteFamilyMember(@PathVariable Long familyMemberId) throws FamilyMemberNotFoundException {
        return familyMemberService.deleteFamilyMember(familyMemberId);
    }

    @GetMapping(value = "/search-households",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Household> searchHouseholds(SearchHouseholdsReq searchHouseholdsReq) throws SearchHouseholdException {
        return householdService.searchHouseholds(searchHouseholdsReq);
    }
}
