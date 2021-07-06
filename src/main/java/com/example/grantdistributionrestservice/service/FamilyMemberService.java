package com.example.grantdistributionrestservice.service;

import com.example.grantdistributionrestservice.model.entity.FamilyMember;
import com.example.grantdistributionrestservice.model.entity.Household;
import com.example.grantdistributionrestservice.model.exceptions.FamilyMemberNotFoundException;
import com.example.grantdistributionrestservice.model.exceptions.HouseholdNotFoundException;
import com.example.grantdistributionrestservice.repository.FamilyMemberRepository;
import com.example.grantdistributionrestservice.repository.HouseholdRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class FamilyMemberService implements IFamilyMemberService {

    @Autowired
    FamilyMemberRepository familyMemberRepository;

    @Autowired
    HouseholdRepository householdRepository;

    @Autowired
    HouseholdService householdService;

    public FamilyMember retrieveFamilyMember(Long familyMemberId) throws FamilyMemberNotFoundException {
        Optional<FamilyMember> oFamilyMember = familyMemberRepository.findById(familyMemberId);

        return oFamilyMember
                .orElseThrow(
                        () -> new FamilyMemberNotFoundException("Family Member with Id: "
                                + familyMemberId + " not found")
                );
    }

    @Override
    @Transactional(rollbackFor = {FamilyMemberNotFoundException.class, HouseholdNotFoundException.class})
    public FamilyMember createFamilyMember(FamilyMember familyMember,
                                           Long householdId,
                                           Long spouseId)
            throws HouseholdNotFoundException, FamilyMemberNotFoundException {

        Household household = householdService.retrieveHousehold(householdId);
        familyMember.setHousehold(household);
        familyMemberRepository.saveAndFlush(familyMember);
        household.getFamilyMembers().add(familyMember);
        householdRepository.saveAndFlush(household);

        if (spouseId != null) {
            FamilyMember spouse = retrieveFamilyMember(spouseId);

            if (spouseId.equals(familyMember.getFamilyMemberId())) {
                throw new FamilyMemberNotFoundException("Unable to set spouse as own self");
            }

            familyMember.setSpouse(spouse);
            spouse.setSpouse(familyMember);
            familyMemberRepository.saveAndFlush(spouse);
            familyMemberRepository.saveAndFlush(familyMember);
        }

        return familyMember;
    }

    @Override
    public FamilyMember deleteFamilyMember(Long familyMemberId) throws FamilyMemberNotFoundException {
        FamilyMember familyMember = retrieveFamilyMember(familyMemberId);

        FamilyMember spouse = familyMember.getSpouse();
        if (spouse != null) {
            spouse.setSpouse(null);
            familyMemberRepository.saveAndFlush(spouse);
        }

        familyMember.setSpouse(null);
        familyMemberRepository.delete(familyMember);

        familyMemberRepository.flush();
        return familyMember;
    }
}
