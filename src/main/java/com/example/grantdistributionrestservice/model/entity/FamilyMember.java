package com.example.grantdistributionrestservice.model.entity;

import com.example.grantdistributionrestservice.model.enums.OccupationTypeEnum;
import com.example.grantdistributionrestservice.validator.ValidateEnum;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FamilyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long familyMemberId;

    @NotBlank(message = "Name is mandatory")
    @Column(nullable = false)
    private String name;
    @NotBlank(message = "Gender is mandatory")
    @Column(nullable = false)
    private String gender;
    @NotBlank(message = "Marital Status is mandatory")
    @Column(nullable = false)
    private String maritalStatus;

    @ValidateEnum(targetClassType = OccupationTypeEnum.class,
            message = "Please provide either UNEMPLOYED, EMPLOYED, STUDENT")
    @NotNull(message = "Occupation Type is mandatory. Please provide either UNEMPLOYED, EMPLOYED, STUDENT")
    @Column(nullable = false)
    private String occupationType;
    @NotNull(message = "Annual Income is mandatory")
    @Min(0)
    @Column(nullable = false)
    private Double annualIncome;
    @Past(message = "Date of Birth has to be a past date")
    @NotNull(message = "Date of Birth is mandatory")
    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDate dateOfBirth;


    @OneToOne
    @JoinColumn
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "familyMemberId")
    @JsonIdentityReference(alwaysAsId = true)
    private FamilyMember spouse;
    @ManyToOne
    @JoinColumn(nullable = false)
    @NonNull
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "householdId")
    @JsonIdentityReference(alwaysAsId = true)
    private Household household;
}
