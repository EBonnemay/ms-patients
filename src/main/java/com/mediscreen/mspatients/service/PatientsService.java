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
    public Patient updatePatient(Patient patientEntityWithUpdates){

        String family = patientEntityWithUpdates.getFamily();
        String given = patientEntityWithUpdates.getGiven();
        Optional<Patient> optPatientToUpdate = patientsRepository.findByFullName(family, given);
        Patient patientToUpdate = optPatientToUpdate.get();
        //patientToUpdate.setFamily(updatedPatient.getFamily());
        //patientToUpdate.setGiven(updatedPatient.getGiven());
        if(patientEntityWithUpdates.getAddress()!=null&&!patientEntityWithUpdates.getAddress().equals(patientToUpdate.getAddress())){
            patientToUpdate.setAddress(patientEntityWithUpdates.getAddress());
        }
        if(patientEntityWithUpdates.getSex()!=null&&!patientEntityWithUpdates.getSex().equals(patientToUpdate.getAddress())){
            patientToUpdate.setSex(patientEntityWithUpdates.getSex());
        }
        if(patientEntityWithUpdates.getDate_of_birth()!=null&&!patientEntityWithUpdates.getDate_of_birth().equals(patientToUpdate.getDate_of_birth())){
            patientToUpdate.setDate_of_birth(patientEntityWithUpdates.getDate_of_birth());
        }
        if(patientEntityWithUpdates.getPhone()!=null&&!patientEntityWithUpdates.getPhone().equals(patientToUpdate.getPatient_id())){
            patientToUpdate.setAddress(patientEntityWithUpdates.getAddress());
        }
        if(patientEntityWithUpdates.getAddress()!=null&&!patientEntityWithUpdates.getAddress().equals(patientToUpdate.getAddress())){
            patientToUpdate.setPhone(patientEntityWithUpdates.getPhone());
        }

        //patientToUpdate.setSex(updatedPatient.getSex());
        //patientToUpdate.setDate_of_birth(updatedPatient.getDate_of_birth());
        //patientToUpdate.setPhone(updatedPatient.getPhone());
        patientsRepository.save(patientToUpdate);
        return patientToUpdate;

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