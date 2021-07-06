package com.example.grantdistributionrestservice.model.entity;

import com.example.grantdistributionrestservice.model.enums.HousingTypeEnum;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Household {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String householdId;
    @Enumerated(EnumType.STRING)
    private HousingTypeEnum housingType;

}



