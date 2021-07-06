package com.example.grantdistributionrestservice.model.entity;

import com.example.grantdistributionrestservice.model.enums.OccupationTypeEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FamilyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String familyMemberId;

    private String name;
    private String gender;
    private String maritalStatus;
    private FamilyMember spouse;
    @Enumerated(EnumType.STRING)
    private OccupationTypeEnum occupationType;
    private Double annualIncome;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dateOfBirth;
}
