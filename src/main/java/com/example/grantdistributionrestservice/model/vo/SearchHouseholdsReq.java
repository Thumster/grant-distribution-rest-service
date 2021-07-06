package com.example.grantdistributionrestservice.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchHouseholdsReq {
    @Min(0)
    private Integer householdSizeLT;
    @Min(0)
    private Integer householdSizeMT;
    @Min(0)
    private Double householdIncomeLT;
    @Min(0)
    private Double householdIncomeMT;
    @Min(0)
    private Integer hasAgeLT;
    @Min(0)
    private Integer hasAgeMT;
    private Boolean hasSpouse;
}
