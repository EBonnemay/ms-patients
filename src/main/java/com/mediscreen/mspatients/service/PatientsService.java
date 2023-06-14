package com.mediscreen.mspatients.service;

import com.mediscreen.mspatients.model.Patient;
import org.springframework.stereotype.Service;
import com.mediscreen.mspatients.repository.PatientsRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PatientsService {
    private PatientsRepository patientsRepository;
    public PatientsService(PatientsRepository patientsRepository){
        this.patientsRepository = patientsRepository;

    }

    public Iterable<Patient> getPatients(){
        return patientsRepository.findAll();
    }
    public Patient getPatientById(int id){
        Optional<Patient> opt = patientsRepository.findById(id);
        return opt.get();
    }
    public Patient getPatientByFullName(String family, String given){
        Optional <Patient> opt = patientsRepository.findByFullName(family, given);
        return opt.get();
    }
    public Patient updatePatient(String id, String family, String given, Date dateOfBirth, String sex, String address, String phone){
        Optional<Patient> optPatient = patientsRepository.findById(Integer.parseInt(id));
        Patient patient = optPatient.get();
        patient.setFamily(family);
        patient.setGiven(given);
        patient.setDate_of_birth(dateOfBirth);
        patient.setSex(sex);
        patient.setAddress(address);
        patient.setPhone(phone);
        patientsRepository.save(patient);
        return patient;

    }
    /*public Iterable<Patient> addPatient(Patient newPatient){
        //Iterable<Patient> listOfPatients
        patientsRepository.save(newPatient);
        return patientsRepository.findAll();
    }*/
    public Patient addPatient(String family, String given, Date dob, String sex, String address, String phone){
        Patient newPatient = new Patient(family, given, dob, sex, address, phone);

        patientsRepository.save(newPatient);
        return newPatient;
    }
    public void deletePatient(String family, String given){
        //Iterable<Patient> listOfPatients
        Optional<Patient>optPatientToDelete = patientsRepository.findByFullName(family, given);
        Patient patientToDelete = optPatientToDelete.get();
        int i = patientToDelete.getPatient_id();
        patientsRepository.deleteById(i);
    }

}
