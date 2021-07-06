package com.example.grantdistributionrestservice.service;

import com.example.grantdistributionrestservice.model.entity.FamilyMember;
import com.example.grantdistributionrestservice.model.exceptions.FamilyMemberNotFoundException;
import com.example.grantdistributionrestservice.model.exceptions.HouseholdNotFoundException;

public interface IFamilyMemberService {
    FamilyMember createFamilyMember(FamilyMember familyMember, Long HouseholdId, Long spouseId)
            throws HouseholdNotFoundException, FamilyMemberNotFoundException;

    FamilyMember deleteFamilyMember(Long familyMemberId) throws FamilyMemberNotFoundException;
}
