package com.reportmanagement.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
@Table(name="citizen_plan_info")
public class CitizenPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer citizenId;
    private String citizenName;
    private String gender;
    private String planName;
    private String planStatus;

    private LocalDate planStartDate;
    private LocalDate planEndDate;
    private Double benefitAmount;

    private String denialReason;

    private LocalDate terminatedDate;
    private String terminatedReason;
}
