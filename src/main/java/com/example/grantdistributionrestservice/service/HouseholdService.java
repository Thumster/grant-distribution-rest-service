package com.example.grantdistributionrestservice.service;

import com.example.grantdistributionrestservice.model.entity.FamilyMember;
import com.example.grantdistributionrestservice.model.entity.Household;
import com.example.grantdistributionrestservice.model.exceptions.HouseholdNotFoundException;
import com.example.grantdistributionrestservice.model.exceptions.SearchHouseholdException;
import com.example.grantdistributionrestservice.model.vo.SearchHouseholdsReq;
import com.example.grantdistributionrestservice.repository.HouseholdRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HouseholdService implements IHouseholdService {
    @Autowired
    HouseholdRepository repository;

    @Override
    public Household create(Household household) {
        log.info("Creating household...");

        repository.save(household);
        repository.flush();

        log.info("Done creating household! householdId: " + household.getHouseholdId());
        return household;
    }

    @Override
    public Household retrieveHousehold(Long householdId) throws HouseholdNotFoundException {
        Household household = repository.findById(householdId)
                .orElseThrow(() -> new HouseholdNotFoundException("Household with Id: " + householdId + " not found"));

        log.info("Successfully retrieved household: " + householdId);
        household.getFamilyMembers().forEach(fm -> {
            log.info("\tFamily Member: " + fm.getFamilyMemberId());
        });

        return household;
    }

    @Override
    public List<Household> retrieveAllHouseholds() {
        List<Household> households = repository.findAll();
        households.forEach(h -> {
            log.info("Successfully retrieved household: " + h.getHouseholdId());
            h.getFamilyMembers().forEach(fm -> {
                log.info("\tFamily Member: " + fm.getFamilyMemberId());
            });
        });
        return households;
    }

    @Override
    public Household deleteHousehold(Long householdId) throws HouseholdNotFoundException {
        Household household = retrieveHousehold(householdId);
        repository.delete(household);
        repository.flush();
        return household;
    }

    @Override
    public List<Household> searchHouseholds(SearchHouseholdsReq req) throws SearchHouseholdException {
        Integer householdSizeLT = req.getHouseholdSizeLT();
        Integer householdSizeMT = req.getHouseholdSizeMT();
        Double householdIncomeLT = req.getHouseholdIncomeLT();
        Double householdIncomeMT = req.getHouseholdIncomeMT();
        Integer hasAgeLT = req.getHasAgeLT();
        Integer hasAgeMT = req.getHasAgeMT();
        Boolean hasSpouse = req.getHasSpouse();

        // Validation Checks
        Map<String, String> errors = new TreeMap<>();
        if (householdSizeLT != null && householdSizeMT != null && householdSizeMT >= householdSizeLT) {
            errors.put("householdSize", "Household Size cannot be more than " +
                    householdSizeMT + " and less than " + householdSizeLT);
        }

        if (householdIncomeLT != null && householdIncomeMT != null && householdIncomeMT >= householdIncomeLT) {
            errors.put("householdIncome", String.format("Household Income cannot be more than %.2f and less than %.2f",
                    householdIncomeMT, householdIncomeLT));
        }

        if (!errors.isEmpty()) {
            throw new SearchHouseholdException("Search parameters provided invalid", errors);
        }
        List<Predicate<Household>> householdConditions = new ArrayList<>();
        if (householdSizeLT != null) {
            Predicate<Household> cond = hh ->
                    hh.getFamilyMembers().size() < householdSizeLT;
            householdConditions.add(cond);
        }
        if (householdSizeMT != null) {
            Predicate<Household> cond = hh ->
                    hh.getFamilyMembers().size() > householdSizeMT;
            householdConditions.add(cond);
        }
        if (householdIncomeMT != null) {
            Predicate<Household> cond = hh ->
                    hh.getFamilyMembers().stream()
                            .mapToDouble(FamilyMember::getAnnualIncome).sum() > householdIncomeMT;
            householdConditions.add(cond);
        }
        if (householdIncomeLT != null) {
            Predicate<Household> cond = hh ->
                    hh.getFamilyMembers().stream()
                            .mapToDouble(FamilyMember::getAnnualIncome).sum() < householdIncomeLT;
            householdConditions.add(cond);
        }
        if (hasAgeLT != null) {
            Predicate<Household> cond = hh ->
                    hh.getFamilyMembers().stream().anyMatch(fm ->
                            Period.between(fm.getDateOfBirth(), LocalDate.now()).getYears() < hasAgeLT
                    );
            householdConditions.add(cond);
        }
        if (hasAgeMT != null) {
            Predicate<Household> cond = hh ->
                    hh.getFamilyMembers().stream()
                            .anyMatch(fm ->
                                    Period.between(fm.getDateOfBirth(), LocalDate.now()).getYears() > hasAgeMT
                            );
            householdConditions.add(cond);
        }
        if (hasSpouse != null && hasSpouse) {
            Predicate<Household> cond = hh ->
                    hh.getFamilyMembers().stream()
                            .filter(fm -> fm.getSpouse() != null)
                            .anyMatch(fm -> hh.getFamilyMembers().stream()
                                    .anyMatch(
                                            fmCheck -> fm.getSpouse().getFamilyMemberId()
                                                    .equals(fmCheck.getFamilyMemberId())

                                    )
                            );
            householdConditions.add(cond);
        }

        if (hasSpouse != null && !hasSpouse) {
            log.info("Entered hasSpouse = FALSE");
            Predicate<Household> cond = hh ->
                    hh.getFamilyMembers().stream()
                            .filter(fm -> fm.getSpouse() != null)
                            .noneMatch(fm -> hh.getFamilyMembers().stream()
                                    .anyMatch(
                                            fmCheck -> fm.getSpouse().getFamilyMemberId()
                                                    .equals(fmCheck.getFamilyMemberId())
                                    )
                            );
            householdConditions.add(cond);
        }

        return repository.findAll().stream().filter(hh -> householdConditions.stream()
                .allMatch(cond -> cond.test(hh)))
                .collect(Collectors.toList());
    }
}
