package com.example.grantdistributionrestservice.model.entity;

import com.example.grantdistributionrestservice.model.enums.HousingTypeEnum;
import com.example.grantdistributionrestservice.validator.ValidateEnum;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Household {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long householdId;
    @ValidateEnum(targetClassType = HousingTypeEnum.class, message = "Please provide either LANDED, CONDOMINIUM, HDB")
    @NotNull(message = "Housing Type is mandatory. Please provide either LANDED, CONDOMINIUM, HDB")
    @Column(nullable = false)
    private String housingType;

    @OneToMany(mappedBy = "household")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<FamilyMember> familyMembers;

    public Household() {
        familyMembers = new ArrayList<>();
    }

    public Household(String housingType) {
        this.housingType = housingType;
    }
}



