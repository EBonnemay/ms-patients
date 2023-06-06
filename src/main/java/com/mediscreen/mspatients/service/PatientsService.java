package com.mediscreen.mspatients.service;

import com.mediscreen.mspatients.model.Patient;
import org.springframework.stereotype.Service;
import com.mediscreen.mspatients.repository.PatientsRepository;

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
    /*public Patient getPatientByFullName(String family, String given){
        Optional <Patient> opt = patientsRepository.findByFullName(family, given);
        return opt.get();
    }*/
    /*public Patient updatePatient(Patient updatedPatient){

        String family = updatedPatient.getName();
        String given = updatedPatient.getGiven();
        Optional<Patient> optPatientToUpdate = patientsRepository.findByFullName(family, given);
        Patient patientToUpdate = optPatientToUpdate.get();
        patientToUpdate.setAddress(updatedPatient.getAddress());
        patientToUpdate.setSex(updatedPatient.getSex());
        patientToUpdate.setPhone(updatedPatient.getPhone());
        patientsRepository.save(patientToUpdate);
        return patientToUpdate;

    }*/
    public Iterable<Patient> addPatient(Patient newPatient){
        //Iterable<Patient> listOfPatients
        patientsRepository.save(newPatient);
        return patientsRepository.findAll();
    }
    /*public Iterable<Patient> deletePatient(String family, String given){
        //Iterable<Patient> listOfPatients
        Optional<Patient>optPatientToDelete = patientsRepository.findByFullName(family, given);
        Patient patientToDelete = optPatientToDelete.get();
        int i = patientToDelete.getPatient_id();
        patientsRepository.deleteById(i);
        return patientsRepository.findAll();
    }*/

}
