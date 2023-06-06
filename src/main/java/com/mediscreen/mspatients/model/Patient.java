package com.mediscreen.mspatients.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
@Entity
@Table(name = "table_patients")

public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int patient_id;

    @Column(name = "family")
    private String family;

    @Column(name = "given")
    private String given;
    @Column(name = "date_of_birth")
    private Date date_of_birth;

   @Column(name = "sex")
    private String sex;

   @Column(name = "address")
    private String address;

   @Column(name = "phone")
    private String phone;

}
