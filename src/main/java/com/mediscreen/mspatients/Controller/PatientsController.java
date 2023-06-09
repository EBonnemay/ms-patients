package com.mediscreen.mspatients.Controller;

import com.mediscreen.mspatients.exceptions.PatientNotFoundException;
import com.mediscreen.mspatients.model.Patient;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mediscreen.mspatients.service.PatientsService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Date;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@RestController
public class PatientsController {

    static final Logger logger = LogManager.getLogger();
    private PatientsService patientsService;


    public PatientsController(PatientsService patientsService) {
        this.patientsService = patientsService;
    }

    @GetMapping("/patients")
    public Iterable<Patient> getPatients() {
        logger.info("inside getPatients");
        return patientsService.getPatients();
    }
    //@GetMapping("/patient/{id}")
    //public Patient getPatientById(@RequestParam Integer id){
    //return patientsService.getPatientById(id);
//
    @GetMapping("patient/{id}")
    public Patient getPatientById(@PathVariable Integer id){
        try {
            return patientsService.getPatientById(id);
        }
        catch(NoSuchElementException noSuchElementException) {
            throw new PatientNotFoundException("no patient with this id ");
        }
    }

        @GetMapping("/patientByFullName")
        public Patient getPatientByName(@Valid @RequestParam String family, @Valid @RequestParam String given){
            return patientsService.getPatientByFullName(family, given);
        }

        @PutMapping("/patient/update")
        public Patient updatePatient(@Valid @RequestBody Patient updatedPatientEntity){
           Patient result= new Patient();
            try{
                result = patientsService.updatePatient(updatedPatientEntity);
                logger.info("person updated successfully");
                return result;
            }catch(RuntimeException e){
                logger.error("person not found");
                throw new PatientNotFoundException("patient not found");
            }
        }

       /* @PostMapping("/patient/add")
        public Iterable<Patient> addPatient(@RequestBody Patient newPatient) {
            Iterable<Patient> listOfPatients = new ArrayList<>();
            try {
                listOfPatients = patientsService.addPatient(newPatient);
                logger.info("person added successfully");
                return listOfPatients;
            } catch (RuntimeException e) {
                logger.error("item not found");
                return listOfPatients;
            }
        }*/

    //curl -d "family=TestNone&given=Test&dob=1966-12-31&sex=F&address=1 Brookside St&phone=100-222-3333" -X POST http://localhost:8081/patient/add

            @PostMapping("/patient/add")
            public ResponseEntity<Patient> addPatient(@Valid @RequestParam String family, @Valid @RequestParam String given,  @RequestParam (value = "dob", required = false) Date dob, @RequestParam (value = "sex", required = false)String sex, @RequestParam (value = "address", required = false)String address, @RequestParam (value = "phone", required = false)String phone){

                try {
                    Patient patient = patientsService.addPatient(family, given, dob, sex, address, phone);
                    logger.info("person added successfully");
                    URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().replacePath("/patient/{id}").buildAndExpand(patient.getPatient_id()).toUri();
                    logger.info("uri location is "+ location.toString());
                    return ResponseEntity.created(location).build();
                } catch (RuntimeException e) {
                    logger.error("item not found");
                    return ResponseEntity.noContent().build();
                    }

            }

    @DeleteMapping("/patient/delete")
        public void deletePatient(@Valid @RequestParam String family, @Valid @RequestParam String given){
            Iterable<Patient> listOfPatients = new ArrayList<>();
            try {
                patientsService.deletePatient(family, given);
                logger.info("person deleted successfully");
            } catch (RuntimeException e) {
                logger.error("item not found");//
            }
        }


}
