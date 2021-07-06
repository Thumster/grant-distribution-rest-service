package com.example.grantdistributionrestservice.model.vo;

import com.example.grantdistributionrestservice.model.entity.FamilyMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateFamilyMemberReq {
    @Valid
    @NotNull(message = "Family Member to create is mandatory")
    private FamilyMember familyMemberToCreate;
    @NotNull(message = "Household Id is mandatory")
    private Long householdId;
    private Long spouseId;
}
